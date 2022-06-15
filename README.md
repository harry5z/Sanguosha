


## Table of Content

1.  [Overview](#overview)
2. [How To Play](#how-to-play)
3. [Architecture](#architecture)
    - [Model](#model)
    - [View](#view)
    - [Controller](#controller)
    - [Event-driven flow](#event-driven-game-flow)
4. [System Design](#system-design)
    - [Overview](#system-overview)
    -  [Extensibility](#extensibility)
     - [Security](#security)
    - [Recovery](#recovery)
    - [Performance](#performance)
    - [Learnability](#learnability)
6. [TODOs/Further Thought](#todo)

## Overview
[Sanguosha](http://en.wikipedia.org/wiki/Legends_of_the_Three_Kingdoms), or loosely translated as "Battles of the Three Kingdoms", is a multiplayer turn-based board game. This project implements Sanguosha as an online multiplayer game.

**Implementation**: It includes both the game server written in Java and a game client implemented with Java Swing library. The server and client communicate via Java Socket.

**Size**: As of 06/11/2022, about 20K lines of code.

<details>
  <summary><b>Click to see details</b></summary>
  
Sanguosha was a popular board game in China. The game is based on the historical events of the [Three Kingdoms](https://en.wikipedia.org/wiki/Three_Kingdoms), a historical period in ancient China around 220–280 AD. See also [Sanguosha official website (in Chinese)](http://www.sanguosha.com/).

The core part of the game is the variety of heroes and their skills, which by an implicit rule maintain that the name and description of a hero's skill should more or less reflect the historical facts about the hero. Many even learned a lot about the history of the Three Kingdoms through playing different heroes. In the early 2010s, many players of the game invented their own versions of Sanguosha, in which heroes are replaced with friends, family members, celebrities, etc. Most of these "heroes" also abide by the rule of accuracy of facts. For example, a student with skill "diligence" (whose effect may be "drawing extra X cards each turn"), a teacher with skill "homework", or perhaps Obama with skill "medicare" (description may be "let a player discard 1 card to regain 1 HP when their HP is below 2").

However, most of these designs were limited to a graphic design of the hero card, as actually printing and playing these hero cards were infeasible to most people. The player base was primarily students busy at schoolwork who had little time to gather for a game, which usually lasts more than 20 minutes. Additionally, due to the complex system of rules of Sanguosha, face-to-face games are prone to human errors (e.g. forgetting to trigger a passive skill), which sometimes can be very frustrating. As an example, see [this page](https://gltjk.com/sanguosha/rules/), also in Chinese, for a detailed breakdown of rules and game flow.

These issues were the original motives of my project. A big fan of Sanguosha, I wanted to build a secure, consistent, and extensible Sanguosha framework that allowed my friends and I to play online with few errors in game flow and allowed easy addition of custom "heroes" and skills.
</details>

## How to play

### Play on live server
- [On Windows](https://github.com/harry5z/Sanguosha/releases/tag/windows)
- [On any platform with JDK 18 or above](https://github.com/harry5z/Sanguosha/releases/tag/jar)
### Run/Test locally
- Run [Server.java](https://github.com/harry5z/Sanguosha/blob/master/src/net/server/Server.java) without arguments
- Run any number of [Client.java](https://github.com/harry5z/Sanguosha/blob/master/src/net/client/Client.java) without arguments.

## Architecture

Both the game server and Java Swing client are MVC based and event-driven to maximize extensibility and modularity. Several components are used by both server and client because of their similarity.

#### Model
The game essentially evolves around the states of its [`Player`s](https://github.com/harry5z/Sanguosha/blob/master/src/core/player/Player.java), each of whom has a [`Role`](https://github.com/harry5z/Sanguosha/blob/master/src/core/player/Role.java), a [`Hero`](https://github.com/harry5z/Sanguosha/blob/master/src/core/heroes/original/AbstractHero.java), some [`Card`s](https://github.com/harry5z/Sanguosha/blob/master/src/cards/Card.java), and some statuses such as Health Points. The game ends when a player of certain `Role` dies, see [game-end conditions](https://github.com/harry5z/Sanguosha/blob/master/src/core/server/game/controllers/mechanics/DeathResolutionGameController.java#L85-L100).

The majority of player information is public (e.g. a player's `Hero`, [`Equipment`s](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/Equipment.java), HP) to all players. The only information private to a player themselves is their hand (of cards) and their `Role` (except the Emperor), which is revealed only upon death. As such, a player sees other players as [`SimplePlayer`s](https://github.com/harry5z/Sanguosha/blob/master/src/core/player/PlayerSimple.java) without `Role` and hand information, and themselves as a [`CompletePlayer`](https://github.com/harry5z/Sanguosha/blob/master/src/core/player/PlayerComplete.java) with all information available.

- At [server side](https://github.com/harry5z/Sanguosha/blob/master/src/core/server/game/GameImpl.java#L49), all `Player`s are initialized as `CompletePlayer` as server is omniscient.
- At [client side](https://github.com/harry5z/Sanguosha/blob/master/src/ui/game/GamePanelGui.java#L37-L38), only a player themselves is initialized as `CompletePlayer`, while all other players are represented as `SimplePlayer`.

#### View

The view on both server and client is represented by a number of [`Listener`s](https://github.com/harry5z/Sanguosha/blob/master/src/core/player/PlayerSimple.java#L28-L33) on `Player` reacting to state changes such as receiving/losing `Card`s, gaining/losing HP.

- At server side, Server-side Listeners are [synchronizers](https://github.com/harry5z/Sanguosha/blob/19b096cae589f2753832fec03ae45a420ff64e32/src/listeners/game/server/ServerInGamePlayerListener.java) that simply send [`SyncCommand`s](https://github.com/harry5z/Sanguosha/blob/master/src/commands/client/game/sync/SyncGameClientCommand.java) to clients to update client-side player statuses, see [example](https://github.com/harry5z/Sanguosha/blob/master/src/commands/client/game/sync/player/SyncHealthCurrentGameClientCommand.java).
- At client side, Client-side Listeners are [GUI components](https://github.com/harry5z/Sanguosha/tree/master/src/ui/game) that visually shows the statuses to the human player, see [example](https://github.com/harry5z/Sanguosha/blob/master/src/ui/game/LifebarGui.java#L19).

#### Controller

- At server side, the game employs a Stack of [`GameController`s](https://github.com/harry5z/Sanguosha/blob/master/src/core/server/game/controllers/AbstractGameController.java) that control the gameflow and modify Players, each with a [`Stage`](https://github.com/harry5z/Sanguosha/blob/master/src/core/server/game/controllers/mechanics/AttackResolutionGameController.java#L27-L38)  to track the progress of its own lifecycle. See an [example](https://github.com/harry5z/Sanguosha/blob/master/src/core/server/game/controllers/mechanics/HealGameController.java#L37-L56).
- At client side, there is no "Controller". The client is designed with Server-driven UI, specifically, **only a [`SyncCommand`](https://github.com/harry5z/Sanguosha/blob/master/src/commands/client/game/sync/SyncGameClientCommand.java) from server may modify client-side Model**, which must always be consistent with server.

#### Event-driven Game Flow

Sanguosha is characterized by a turn-based game flow with many actively or passively triggered Hero Skills and Equipment abilities, which frequently interrupt the game to wait for player actions to proceed.

At server side, the game [continuously drive the current `GameController`](https://github.com/harry5z/Sanguosha/blob/master/src/core/server/game/GameImpl.java#L217-L230) (which may push other `GameController`s on top of itself), until a player action is required and the `GameController` throws a [`GameFlowInterrupedException`](https://github.com/harry5z/Sanguosha/blob/19b096cae589f2753832fec03ae45a420ff64e32/src/exceptions/server/game/GameFlowInterruptedException.java) to "pause" the game and send a [`PlayerActionGameClientCommand`](https://github.com/harry5z/Sanguosha/blob/master/src/commands/client/game/PlayerActionGameClientCommand.java) to request player action. The player may respond with an [`InGameServerCommand`](https://github.com/harry5z/Sanguosha/blob/master/src/commands/server/ingame/InGameServerCommand.java) (or server takes a [default response](https://github.com/harry5z/Sanguosha/blob/master/src/commands/client/game/PlayerActionGameClientCommand.java#L55-L62) upon timeout), which "resumes" the game until the next player action is required.

## System Design

### Overview <a id="system-overview"></a>

The design concerns are listed below ranked by importance (high to low):

##### Tier 1
 - [**Extensibility**](#extensibility): The framework must be extensible to allow easy addition of new heroes, skills, features, etc.
 - [**Security**](#security): The game must avoid cheating, leaking, and sniffing for personal information, etc.

##### Tier 2
- [**Recovery**](#recovery): Must be internally tolerant to errors like race conditions, and externally tolerant to user failures like internet disconnection.
- [**Performance**](#performance): Minimize network usage, memory & CPU usage, and avoid memory leaks.
##### Tier 3
- [**Learnability/Maintainability (of the codebase)**](#learnability): The framework must be intuitive to learn and minimize errors due to human oversights.

A few other common design concerns have been deprioritized for various reasons, listed below:
- **Scalability**: As a turn-based game, the frequency of client-server communication is significantly lower than a real-time game. If server capacity is reached, we can move ongoing games (the most traffic-intense part) to dedicated servers.
- **Cross-platform API**: For the first iteration, the game is built with a Java Swing client communicating with server via Socket. In future iterations, a generic API for cross-platform clients (e.g. Android/iOS, web, PC/Mac native app) can be built on top of the existing framework.
- **Unit/Integration Testing**: In my opinion, testing is necessary for a real-world online game, but the workload overhead is beyond the capability of one developer.

Additionally, the game client's *UI and UX are deprioritized*, because (1) the first iteration focuses on functionality and proof of concept, and (2) players of this game should already know how to play without hints, as they should have played the in-person board game already.

---

#### Extensibility

The MVC architecture is built so that new heroes and skills can be added quickly, with minimum edits to existing code. For example, see the commits for:

- [Wei Yan](https://github.com/harry5z/Sanguosha/commit/4a68e5f8d622d4c117df2a4581a8a48d4f736bbb), a hero with a passive skill (no player action). 100 lines.
- [Huang Zhong](https://github.com/harry5z/Sanguosha/commit/e370a1f679d13b00bb62037a1431e516458f9990), a hero with a passively-triggered skill (player must confirm). 150 lines.
- [Yuan Shao](https://github.com/harry5z/Sanguosha/commit/0a053bc3dbd6a77fab0a23fc8cbee8e2e5e17270), a hero with an active skill (player proactively activates). 240 lines.

#### Security

The game's security consists of 3 parts, Anti-cheat, Anti-leak, and Anti-sniff.

- Anti-cheat: there are
    - **Illegal Player Actions**. A player may attempt to set an invalid target, use a Card/Equipment/Skill they do not have, use fewer/more than required Cards, etc.
        - Solution 1: Set [Allowed Action Types](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/commands/client/game/PlayerActionGameClientCommand.java#L45-L53) to accept only valid actions. See [example](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/commands/client/game/RequestNullificationGameUIClientCommand.java#L52-L69).
        - Solution 2: If an action type is allowed, also [sanity-check the action](https://github.com/harry5z/Sanguosha/blob/master/src/commands/server/ingame/InGameServerCommand.java#L46-L54). See [example](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/commands/server/ingame/InitiateAttackInGameServerCommand.java#L53-L100).
    - **Game Flow Disruption**. A player may attempt to act when not allowed to, or the game may receive a previously valid but currently illegal action due to network delay.
        - Solution: Send a server-generated [Response ID](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/commands/client/game/PlayerActionGameClientCommand.java#L30-L43) to accept actions only from players who may act. See [example](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/commands/client/game/RequestNullificationGameUIClientCommand.java#L40-L50).

    - **Impersonation**. A player may attempt to act on behalf of another player.
        - Solution: Set action's source [at server-side](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/core/server/GameRoom.java#L77-L82).

- Anti-leak: The game does not send to players any information they should not know of. For example, a player's hand is private, so when a player receives/loses a Card, only that player is sent the [concrete Card information](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/commands/client/game/sync/player/SyncPlayerCardGameClientCommand.java#L11-L12), whereas other players only receive [a command to increment/decrement that player's card count](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/commands/client/game/sync/player/SyncOtherPlayerCardGameClientCommand.java#L13-L15).

- Anti-sniff: The client-server communication is currently unencyrpted, because no password information is present. However, it is therefore vulnerable to man-in-the-middle attacks. See [TODO list](#todo).

#### Recovery

##### Player Reconnection

If a player disconnects while in-game, the server [marks them as disconnected](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/core/server/OnlineUserManager.java#L95-L114), and if they reconnects in time, they will be [redirected back into the game](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/core/server/OnlineUserManager.java#L73-L83) and [sent the latest game states](https://github.com/harry5z/Sanguosha/blob/88c65e79ee47c05dfa966539077e40d78de00c3b/src/core/server/game/GameImpl.java#L185-L203), so that they can resume playing.
If a player disconnects while not in-game, they're considered logged out.

#### Performance

As a turn-based game, the server sends frequent updates to clients while in-game, while clients only send sporadic actions to server.
- [Throttling](https://github.com/harry5z/Sanguosha/blob/master/src/net/server/ServerConnection.java#L41-L47): Server periodically flushes all updates sent to a client. Current "server tick" interval is *0.1s*
- [Aggregated Request](https://github.com/harry5z/Sanguosha/blob/master/src/net/server/ServerConnection.java#L140-L143): All updates to be sent to a client during a server tick is combined into a single request

#### Learnability

As the project grows in size, components must be easily understandable and built to avoid human errors such as using the wrong method by accident. For example:

- [AbstractMultiCardMultiTargetOperation](https://github.com/harry5z/Sanguosha/blob/master/src/core/client/game/operations/AbstractMultiCardMultiTargetOperation.java#L205-L269): A generic template that controls how the player interacts with game UI. Most player actions can be easily and correctly implemented this way, see [example](https://github.com/harry5z/Sanguosha/blob/master/src/core/client/game/operations/equipment/SerpentSpearInitiateAttackOperation.java).

## TODO List <a id="todo"></a>

#### Server-side

- [ ] Build generic JSON-based API for clients on different platforms
- [ ] Add HTTPS-like encryption for client-server communication
- [ ] Cover major game flows with integration tests
- [ ] Add CPU & Memory monitoring and create alerts

#### Client-side
- [x] [Synchronous Execution](https://github.com/harry5z/Sanguosha/blob/master/src/net/client/ClientConnection.java#L70-L85). Make sure to execute updates sent by the server in chronological order
- [ ] Prettify client UI and add animations
- [ ] Allow Chats