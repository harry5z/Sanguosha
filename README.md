Sanguosha
=========
Sanguosha, or translated as "Battles of the Three Kingdoms", is a very famous board game in China today, based on the historical events of the Three Kingdoms (a historical period in ancient China, around 220–280 AD). (see link below)

http://en.wikipedia.org/wiki/Legends_of_the_Three_Kingdoms

Official website (English, incomplete):
http://legendsofthethreekingdoms.com/

Official website (Chinese, complete):
http://www.sanguosha.com/

The core and most fun part of the game is the variety of heroes and their skills, as you may have already noticed, there are many expansion packs of heroes. An implicit rule is that the names and descriptions of a hero's skills should more or less match the historical facts about the hero. Many people even learn a lot about the history of the Three Kingdoms through playing different heroes.

Therefore, besides the official game, many gamers invented their own versions of Sanguosha, in which heroes are replaced with friends, family members, celebrities, etc. Most of these "heroes" also abide by the rule of similarity, for example, a student with skill "study hard", a teacher with skill "homework", or perhaps Obama with skill "medicare". However, most of these designs are limited to a graphical design of the hero card, because actually printing and playing these hero cards are infeasible to most people -- most players are students who are busy at schoolwork as well, and it is hard to gather them together to play a game, which usually lasts more than 20 minutes; besides, due to the complicated system of rules of Sanguosha, face-to-face games are prone to error, which sometimes can be very frustrating.

There is an online version of Sanguosha, run by the company that designed it. The use of computer largely reduces human errors in face-to-face games, so Sanguosha online is gaining popularity. Nonetheless, it does not quite meet all our needs. First, players are limited to play only the official heroes. Second, most heroes (especially the better ones) are paid, and given that there are so many expansion packs, it costs a considerable amount of cash to purchase all heroes.

And these are the motives of my project. A big fan of Sanguosha, I want to build a Sanguosha framework that my friends and I can use to play over the network. Since I wrote the source code, it is easy to add heroes and skills to the game, so one day we can perhaps complete a "CMU-sha" (Battles of CMU), that would be really fun. Best of all, it's free.

My ultimate goal is to automatize the creation of heroes and skills by designing a framework that supports the creation of common types of skills by several simple selections and keystrokes.


How to play (As of 1/3/2014):

1. Run Master.java without arguments (a small window will appear)

2. Run several Client.java without arguments

3. Click "start" on the small window.


Current Progress:

As of 1/3/2014:

1. Basic documentation

2. Master and Client:
It is tested that multiple clients on different machines within an LAN can play together. Remote connection is not tested yet, because there appears to be some router configurations required, and I forgot the username and password of my router...

3. Basic Gui: cards, players, buttons, life bar, etc.

4. Game flow

5. Near-death and death events:
Whenever a player's current health drops below 1, the player enters near-death stage. Starting from the player who is playing the current turn, each player (including dying player himself) decides whether to use peach to save him. If enough peaches are given and the dying player's health returns to 1 or more, the game continues; if not enough peaches are given after a cycle, then the player dies. 

6. DrawCard stage and Discard stage:
At draw card stage, a player draws 2 cards (by default) from deck, then enters dealing stage. After player chooses to end dealing stage, the player enters discard stage: if the player owns more cards on hand than "cardsOnHandLimit" (by default equal to player's current health), the players have to discard extra cards.

7. All 4 basic-type cards are enabled:
Attack: used by a player during turn_deal to attack other players that are in player's attack range. By default, a player can use only 1 attack per turn, a player's attack distance is initially 1, and an attack carries 1 damage.

Dodge: used to dodge attacks from other players, used only when player is attacked. If player chooses to dodge, the
attack is cancelled, otherwise, the player takes the damage carried by the attack

Peach: used to increase a player's health during turn_deal, or to save a player who is dying. Note that a player's current health can never exceeds health limit.

Wine: used to increase the damage carried by player's next attack (this turn) by 1 during turn_deal, or to save a player HIMSELF when he is dying. By default, wine (used to increase damage) can be used once per turn.
