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


#### How to play (As of 1/3/2014):

1. Run [Server.java](https://github.com/harry5z/Sanguosha/blob/master/src/net/Server.java) without arguments (a small window will appear)

2. Run several [Client.java](https://github.com/harry5z/Sanguosha/blob/master/src/net/Client.java) without arguments

3. Click "start" on the small window.


#### Current Progress:

#### As of 1/3/2014:

0. Basic documentation

1. Core functionalities such as [cards](https://github.com/harry5z/Sanguosha/blob/master/src/cards/Card.java), [players](https://github.com/harry5z/Sanguosha/tree/master/src/player), [updates](https://github.com/harry5z/Sanguosha/blob/master/src/update/Update.java) etc.

2. [Server](https://github.com/harry5z/Sanguosha/blob/master/src/net/Server.java) and [Client](https://github.com/harry5z/Sanguosha/blob/master/src/net/Client.java):
It is tested that multiple clients on different machines within an LAN can play together. Remote connection is not tested yet, because there appears to be some router configurations required, and I forgot the username and password of my router...

3. Basic [Gui](https://github.com/harry5z/Sanguosha/tree/master/src/gui): [cards](https://github.com/harry5z/Sanguosha/blob/master/src/gui/CardGui.java), [players](https://github.com/harry5z/Sanguosha/blob/master/src/gui/PlayerGui.java), [buttons](https://github.com/harry5z/Sanguosha/blob/master/src/gui/ButtonGui.java), [life bar](https://github.com/harry5z/Sanguosha/blob/master/src/gui/LifebarGui.java), etc.

4. [Game flow](https://github.com/harry5z/Sanguosha/blob/master/src/update/Stage.java)

5. [Near-death](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/NearDeathOperation.java) and [death](https://github.com/harry5z/Sanguosha/blob/master/src/update/DeathEvent.java) events:
Whenever a player's current health drops below 1, the player enters near-death stage. Starting from the player who is playing the current turn, each player (including dying player himself) decides whether to use peach to save him. If enough peaches are given and the dying player's health returns to 1 or more, the game continues; if not enough peaches are given after a cycle, then the player dies. 

6. [DrawCard](https://github.com/harry5z/Sanguosha/blob/master/src/update/DrawCardsFromDeck.java) stage and [Discard](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/TurnDiscardOperation.java) stage:
At draw card stage, a player draws 2 cards (by default) from [deck](https://github.com/harry5z/Sanguosha/blob/master/src/core/Deck.java), then enters dealing stage. After player chooses to end dealing stage, the player enters discard stage: if the player owns more cards on hand than "cardsOnHandLimit" (by default equal to player's current health), the players have to discard extra cards.

7. All 4 [Basic](https://github.com/harry5z/Sanguosha/blob/master/src/cards/basics/Basic.java) cards are enabled:

[Attack](https://github.com/harry5z/Sanguosha/blob/master/src/cards/basics/Attack.java): used by a player during turn_deal to attack other players that are in player's attack range. By default, a player can use only 1 attack per turn, a player's attack distance is initially 1, and an attack carries 1 damage.

[Dodge](https://github.com/harry5z/Sanguosha/blob/master/src/cards/basics/Dodge.java): used to dodge attacks from other players, used only when player is attacked. If player chooses to dodge, the
attack is cancelled, otherwise, the player takes the damage carried by the attack

[Peach](https://github.com/harry5z/Sanguosha/blob/master/src/cards/basics/Peach.java): used to increase a player's health during turn_deal, or to save a player who is dying. Note that a player's current health can never exceeds health limit.

[Wine](https://github.com/harry5z/Sanguosha/blob/master/src/cards/basics/Wine.java): used to increase the damage carried by player's next attack (this turn) by 1 during turn_deal, or to save a player HIMSELF when he is dying. By default, wine (used to increase damage) can be used once per turn.

#### 1/4/2014:

Fixed card equality bug: added unique ID to each real card, also made equality check faster.

#### 1/5/2014:

Added two [instant](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/Instant.java) type cards: [Duel](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/Duel.java) and [Neutralization](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/Neutralization.java). All instant cards are of [special](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/Special.java) type. 

Fixed game flow bug brought up by Duel (when turn_player dies in a duel)

[Neutralization](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/NeutralizationOperation.java): while anyone uses an instant type card, or when a delayed type card is to be decided, any player can deal an Neutralization to cancel the effect of this special-type card. Of course, Neutralization itself is a special-type card so its effect can be neutralized as well.

[Duel](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/DuelOperation.java): Used by a player to duel with another player during turn_deal. Once a target is selected, starting from the target, two players take turns to deal an Attack. The first player that fails to do so takes (by default) 1 damage from the other player.

#### 1/6/2014:

Added two instant type cards: [Creation](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/Creation.java) and [Fire Attack](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/FireAttack.java).

[Creation](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/CreationOperation.java): draw two cards from deck.

[Fire Attack](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/FireAttackOperation.java): Use it against another player or self. Target shows a card on hand, and if attacker can discard a card on hand with the same suit, then target takes 1 damage of element "Fire" from source.

#### 1/10/2014:

Fixed ExecutorService bug: can now add any number of players.

Fixed bug in Neutralization: Dead players will not be asked for Neutralization.

Added three instant cards with [Area of Effect](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/AreaOfEffectOperation.java) property: [Barbarian Invasion](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/BarbarianInvasion.java), [ArrowSalvo](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/ArrowSalvo.java), and [Brotherhood](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/Brotherhood.java).

[Barbarian Invasion](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/BarbarianInvasionOperation.java): From the next player of the current player (who plays this card), each player except source player himself has to play an Attack one by one (the effect of this card can be neutralized on each player too). Those who fails to do so takes 1 damage from source.

[Arrow Salvo](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/ArrowSalvoOperation.java): very similar to Barbarian Invasion, except that each player plays a Dodge.

[Brotherhood](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/BrotherhoodOperation.java): All injured players gain 1 health point (the effect of this card can be neutralized on each player too).

#### 1/17/2014:

Added two [Equipments](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/Equipment.java): [HorsePlus](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/HorsePlus.java) and [HorseMinus](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/HorseMinus.java):

Plus: when a player is equipped the horse, the distance between the player and any other play is increased by 1 (i.e. harder to be reached).

Minus: when a player is equipped the horse, the reaching distance of the player is increase by 1 (i.e. easier to reach others).

General rule of equipments: at most one equipment can be equipped for each type ([weapon](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/weapons/Weapon.java),[shield](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/shields/Shield.java),plus,minus), so when a new equipment is equipped, the old one (of the same type) is discarded.

#### 1/18/2014:

Added two shields: [Iron Shield](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/shields/IronShield.java) and [Ratten Armor](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/shields/RattanArmor.java):

Iron Shield: makes black "Attack" not effective

Rattan Armor: makes Barbarian Invasion, Arrow Salvo, and normal "Attack" [not effective](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/shields/RattanArmor.java#L28-L37). When taking any damage of element Fire, the damage +1 (not yet implemented).

#### 1/28/2014:

Added card selection animation.

#### 1/30/2014:

Added Attack(Fire) and Attack(Thunder) and [Silver Lion](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/shields/SilverLion.java).

Attack(Fire/Thunder): an Attack that carries Fire/Thunder element

Silver Lion: Taking [at most 1 damage](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/shields/SilverLion.java#L40-L44) each time. Gain 1 health point when un-equiping Silver Lion (not yet implemented).

Rattan Armor: [Fire damage +1](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/shields/RattanArmor.java#L39-L44) implemented

#### 1/31/2014:

Added [MessageBox](https://github.com/harry5z/Sanguosha/blob/master/src/gui/MessageBoxGui.java) on main panel. Now players will know what they are reacting to.

#### 2/18/2014:

Bug fix: Bug in [Damage.java](https://github.com/harry5z/Sanguosha/blob/master/src/update/Damage.java), when a health-1 player fireattack himself to near death, after he is rescued, the subsequent stages are out of order.

Major change in structure: [Suit](https://github.com/harry5z/Sanguosha/blob/master/src/cards/Card.java#L17-L20), [Color](https://github.com/harry5z/Sanguosha/blob/master/src/cards/Card.java#L22-L25), [CardType](https://github.com/harry5z/Sanguosha/blob/master/src/cards/Card.java#L27-L30), and EquipmentType are all enum types now. Update becomes an abstract class with the ability to get and set the next update like a Stack.

#### 2/24/2014:

Silver Lion [feature](https://github.com/harry5z/Sanguosha/blob/master/src/cards/equipments/shields/SilverLion.java#L28-L33): "Gain 1 health point when un-equiping" implemented.

Started working on CardSelectionPane (for Sabotage, Steal, Harvest, etc.)

#### 3/03/2014:

Added new Instant type card [Sabotage](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/Sabotage.java) and [Steal](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/Steal.java)

[Sabotage](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/SabotageOperation.java): Can discard one card (on hand, equipment area, or decision area) of another player.

[Steal](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/StealOperation.java): Can take one card (on hand, equipment area, or decision area) of another player that is within distance 1.

[CardSelectionPane](https://github.com/harry5z/Sanguosha/blob/master/src/gui/CardSelectionPane.java) finished (for Sabotage, Steal, Harvest, and future skill implementations)

#### 3/25/2014:

Minor changes in animation: selection of card/player now display a red borderline around the object

Added new Instant type card [Harvest](https://github.com/harry5z/Sanguosha/blob/master/src/cards/specials/instant/Harvest.java)

[Harvest](https://github.com/harry5z/Sanguosha/blob/master/src/update/operations/special_operations/HarvestOperation.java): Draw from deck and display the same number of cards as the number of surviving players, then starting at the player that plays `Harvest`, every player takes turn to draw one from these cards.
