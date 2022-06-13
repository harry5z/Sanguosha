
# Sanguosha

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

## System Design

### Overview

The design concerns are listed below ranked by importance (high to low):

##### Tier 1
 - [**Extensibility**](#extensibility): The framework must be extensible to allow easy addition of new heroes, skills, features, etc.
 - [**Security**](#security): The game must avoid cheating, leaking, and sniffing for personal information, etc.
 - [**Consistency**](#consistency): The game must behave as per the rules, even during complex game flow interactions.
##### Tier 2
- [**Reliability**](#reliability): Must be internally tolerant to errors like race conditions, and externally tolerant to user failures like internet disconnection.
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

The architecture is built so that new heroes and skills can be added quickly, with minimum edits to existing code. For example, see the commits for:

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

#### Consistency

Sanguosha is characterized by a frequently interrupted gameflow due to many passively triggered hero skills and equipment abilities. 

The game employs a ["Stacked"](https://github.com/harry5z/Sanguosha/blob/master/src/core/server/game/GameImpl.java#L222) gameflow mechanism, where each event is represented as a [`GameController`](https://github.com/harry5z/Sanguosha/blob/master/src/core/server/game/controllers/AbstractGameController.java) with [`Stage`](https://github.com/harry5z/Sanguosha/blob/master/src/core/server/game/controllers/mechanics/AttackResolutionGameController.java#L27-L38) that tracks the progress of the event's lifecycle. 

#### Reliability

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

---
## Tasks/TODO <a id="todo"></a>

#### Server-side

- [ ] Build generic JSON-based API for clients on different platforms
- [ ] Add HTTPS-like encryption for client-server communication
- [ ] Cover major game flows with integration tests

#### Client-side
- [x] [Synchronous Execution](https://github.com/harry5z/Sanguosha/blob/master/src/net/client/ClientConnection.java#L70-L85). Make sure to execute updates sent by the server in chronological order
- [ ] Prettify client UI and add animations
- [ ] Allow Chats