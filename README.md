
# Sanguosha

 ## Background 
[Sanguosha](http://en.wikipedia.org/wiki/Legends_of_the_Three_Kingdoms), or loosely translated as "Battles of the Three Kingdoms", was a popular board game in China in the early 2010s (when this project started), based on the historical events of the Three Kingdoms, a historical period in ancient China, around 220–280 AD. See also [official website (in Chinese)](http://www.sanguosha.com/).

The core part of the game is the variety of heroes and their skills, which by an implicit rule maintain that the name and description of a hero's skill should more or less reflect the historical facts about the hero. Many even learned a lot about the history of the Three Kingdoms through playing different heroes. In the early 2010s, many players of the game invented their own versions of Sanguosha, in which heroes are replaced with friends, family members, celebrities, etc. Most of these "heroes" also abide by the rule of accuracy of facts. For example, a student with skill "diligence" (whose effect may be "drawing extra X cards each turn"), a teacher with skill "homework", or perhaps Obama with skill "medicare" (whose effect may be "let a player discard 1 card to regain 1 HP when their HP is below 2").

However, most of these designs were limited to a graphic design of the hero card, as actually printing and playing these hero cards were infeasible to most people. The player base was primarily students busy at schoolwork who had little time to gather for a game, which usually lasts more than 20 minutes. Additionally, due to the complex system of rules of Sanguosha, face-to-face games are prone to human errors (e.g. forgetting to trigger a passive skill), which sometimes can be very frustrating. As an example, see [this page](https://gltjk.com/sanguosha/rules/), also in Chinese, for a detailed breakdown of rules and game flow.

These issues were the original motives of my project. A big fan of Sanguosha, I wanted to build a secure, consistent, and extensible Sanguosha framework that allowed my friends and I to play online with few errors in game flow and allowed easy addition of custom "heroes" and skills.


### How to play (As of 05/26/2022)

1. Run [Server.java](https://github.com/harry5z/Sanguosha/blob/master/src/net/server/Server.java) without arguments

2. Run any number of [Client.java](https://github.com/harry5z/Sanguosha/blob/master/src/net/client/Client.java) without arguments. A window would appear for each Client. Enter a unique name and click "Start".

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
- [**Learnability/Maintainability (of the codebase)**](#learnability): While I am the sole developer for this project, in a real-world scenario, a game of this scale would involve a team of engineers. The framework must be intuitive and minimize errors due to human oversights.

A few other common design concerns have been deprioritized for various reasons, listed below:
- **Scalability**: While scalability is important for a real-world online game, it is beyond the capacity of one developer (myself), and beyond the scope of this project, to build and test a scalable system.
- **Cross-platform API**: While a real-world online game ideally provides a generic API for cross-platform clients(e.g. Android/iOS, web, PC/Mac native app), this project is built specifically over Java Socket for simplicity.
- **Unit/Integration Testing**: In my opinion, testing is necessary for a real-world online game, but the workload overhead is beyond the capability of one developer.

Additionally, the game client's *UI and UX are deprioritized*, because (1) it is way beyond the capacity of a lone developer to build an acceptable UI, and (2) the intended players of this game should already know how to play without hints.

Other minor "engineering best practices" such as proper documentation and single-purpose commits are deprioritized, though I do try to adhere to the best practices as much as possible.

---

#### Extensibility
##### Completion: 5/5

The architecture is built so that new heroes and skills can be added quickly, with minimum edits to existing code. For example, see the commits for:

- [Wei Yan](https://github.com/harry5z/Sanguosha/commit/4a68e5f8d622d4c117df2a4581a8a48d4f736bbb), a hero with a passive skill
- [Huang Zhong](https://github.com/harry5z/Sanguosha/commit/e370a1f679d13b00bb62037a1431e516458f9990), a hero with a passively-triggered skill
- [Yuan Shao](https://github.com/harry5z/Sanguosha/commit/0a053bc3dbd6a77fab0a23fc8cbee8e2e5e17270), a hero with an active skill which involves client-side player UI interaction

#### Security
##### Completion: 5/5

The game's security consists of 3 parts, Anti-cheat, Anti-leak, and Anti-sniff.

Anti-cheat: there are
- Illegal Player Actions, including attempting to use a Card/Equipment/Skill they do not have, setting an invalid target, etc. This is prevented with player action validation.
- Game Flow Disruption. The player may attempt to act when not allowed to, or the game may receive a previously valid but currently illegal action due to network delay. This is prevented with response ID and allowed response types.
- Impersonation. The player may attempt to act on behalf of another player. This is prevented by only using server-side validated action source in received player actions.

Anti-leak: The game does not send to players any information they should not know of. For example, when Player A receives 1 Card, only Player A themselves is sent the concrete Card information, whereas other players only receive a package to increment Player A's card count by 1.

Anti-sniff: The client-server communication is currently unencyrpted, because no password information is present. TODO: implement HTTPS-like encyrption for client-server communication.

#### Consistency
##### Completion: 5/5

#### Reliability
##### Completion: 5/5

Player Reconnection
Client-side incoming command ordering

#### Performance
##### Completion: 5/5

Network usage is minimized with Aggregated Command. Network utilization is trottled.

TODO: Build CPU & memory monitoring system

#### Learnability
##### Completion: 5/5

client-side operation


---

### WIP