Sanguosha
=========
Sanguosha, or translated as "Battles of the Three Kindoms", is a very famous board game in China today, based on the historical events of the Three Kindoms (a historical period in ancient China, around 220–280 AD). (see link below)

http://en.wikipedia.org/wiki/Legends_of_the_Three_Kingdoms

The core and most fun part of the game is the variety of heroes and their skills, as you may have already noticed, there are many expansion packs of heroes. An implicit rule is that the names and descriptions of a hero's skills should more or less match the historical facts about the hero. Many people even learn a lot about the history of the Three Kindoms through playing different heroes.

Therefore, besides the official game, many gamers invented their own versions of Sanguosha, in which heroes are replaced with friends, family members, celebrities, etc. Most of these "heroes" also abide by the rule of similarity, for example, a student with skill "study hard", a teacher with skill "homework", or perhaps Obama with skill "medicare". However, most of these designs are limited to a graphical design of the hero card, because actually printing and playing these hero cards are infeasible to most people -- most players are students who are busy at schoolwork as well, and it is hard to gather them together to play a game, which usually lasts more than 20 minutes; besides, due to the complicated system of rules of Sanguosha, face-to-face games are prone to error, which sometimes can be very frustrating.

There is an online version of Sanguosha, run by the company that designed it. The use of computer largely reduces human errors in face-to-face games, so Sanguosha online is gaining popularity. Nonetheless, it does not quite meet all our needs. First, players are limited to play only the official heroes. Second, most heroes (especially the better ones) are paid, and given that there are so many expansion packs, it costs a considerable amount of cash to purchase all heroes.

And these are the motives of my project. A big fan of Sanguosha, I want to build a Sanguosha framework that my friends and I can use to play over the network. Since I wrote the source code, it is easy to add heroes and skills to the game, so one day we can perhaps complete a "CMU-sha" (Battles of CMU), that would be really fun. Best of all, it's free.

My ultimate goal is to automatize the creation of heroes and skills by designing a framework that supports the creation of common types of skills by several simple selections and keystrokes.


How to play (As of 1/3/2014):

Run Master.java without arguments (a small window will appear)
Run several Client.java without arguments
click "start" on the small window.


Current Progress:

As of 1/3/2014:
1. Basic documentation
2. Master and Client
3. All 4 basic-type cards enabled
4. Attack/Dodge event
5. Basic Gui
6. Game flow
7. Near-death and death events
8. DrawCard stage and Discard stage