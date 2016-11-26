package core;

import static cards.Card.Suit.CLUB;
import static cards.Card.Suit.DIAMOND;
import static cards.Card.Suit.HEART;
import static cards.Card.Suit.SPADE;
import static core.server.game.Damage.Element.FIRE;
import static core.server.game.Damage.Element.NORMAL;
import static core.server.game.Damage.Element.THUNDER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import cards.Card;
import cards.basics.Attack;
import cards.basics.Dodge;
import cards.basics.Peach;
import cards.basics.Wine;
import cards.equipments.HorseMinus;
import cards.equipments.HorsePlus;
import cards.equipments.shields.IronShield;
import cards.equipments.shields.RattanArmor;
import cards.equipments.shields.SilverLion;
import cards.equipments.weapons.IcySword;
import cards.specials.instant.BarbarianInvasion;
import cards.specials.instant.Creation;
import cards.specials.instant.Duel;
import cards.specials.instant.FireAttack;
import cards.specials.instant.Harvest;
import cards.specials.instant.Neutralization;
import utils.CardIDUtil;
import utils.Log;

/**
 * The deck used in game
 * 
 * @author Harry
 *
 */
public class Deck {
	private static final String TAG = "Deck";
	/**
	 * used and discarded cards, will be shuffled once
	 * deck is empty
	 */
	private List<Card> used;
	/**
	 * deck of cards to be drawn from
	 */
	private List<Card> deck;
	private CardIDUtil util;

	public enum DeckPack {
		STANDARD, EX, BATTLE;
	}

	public Deck(Set<DeckPack> packs) {
		used = new ArrayList<Card>();
		deck = new ArrayList<Card>();
		util = new CardIDUtil();
		initializeDeck(packs);
	}

	public Card draw() {
		Card card = deck.remove(deck.size() - 1); // always draw from top
		if (deck.size() == 0) {
			resetDeck();
		}
		return card;
	}

	public List<Card> drawMany(int amount) {
		List<Card> cards = new ArrayList<Card>(amount);
		for (int i = 0; i < amount; i++) {
			cards.add(draw());
		}
		return cards;
	}

	public int getDeckSize() {
		return deck.size();
	}

	public void discard(Card card) {
		used.add(card);
		Log.log(TAG, "card " + card.getName() + " discarded. Used deck size: " + used.size());
	}

	public void discardAll(List<? extends Card> cards) {
		used.addAll(cards);
		Log.log(TAG, cards.size() + " cards are discarded. Used deck size: " + used.size());
	}

	/**
	 * shuffle the used deck and make it the new deck
	 */
	private void resetDeck() {
		deck.addAll(used);
		used.clear();
		Collections.shuffle(deck);
	}

	private void initAttack() {
		used.add(new Attack(NORMAL, 2, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 3, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 4, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 5, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 6, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 6, DIAMOND, util.getUID()));
		used.add(new Attack(NORMAL, 7, SPADE, util.getUID()));
		used.add(new Attack(NORMAL, 7, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 7, DIAMOND, util.getUID()));
		used.add(new Attack(NORMAL, 8, SPADE, util.getUID()));
		used.add(new Attack(NORMAL, 8, SPADE, util.getUID()));
		used.add(new Attack(NORMAL, 8, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 8, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 8, DIAMOND, util.getUID()));
		used.add(new Attack(NORMAL, 9, SPADE, util.getUID()));
		used.add(new Attack(NORMAL, 9, SPADE, util.getUID()));
		used.add(new Attack(NORMAL, 9, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 9, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 9, DIAMOND, util.getUID()));
		used.add(new Attack(NORMAL, 10, SPADE, util.getUID()));
		used.add(new Attack(NORMAL, 10, SPADE, util.getUID()));
		used.add(new Attack(NORMAL, 10, HEART, util.getUID()));
		used.add(new Attack(NORMAL, 10, HEART, util.getUID()));
		used.add(new Attack(NORMAL, 10, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 10, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 10, DIAMOND, util.getUID()));
		used.add(new Attack(NORMAL, 11, HEART, util.getUID()));
		used.add(new Attack(NORMAL, 11, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 11, CLUB, util.getUID()));
		used.add(new Attack(NORMAL, 13, DIAMOND, util.getUID()));
	}

	private void initDodge() {
		used.add(new Dodge(2, HEART, util.getUID()));
		used.add(new Dodge(2, HEART, util.getUID()));
		used.add(new Dodge(2, DIAMOND, util.getUID()));
		used.add(new Dodge(2, DIAMOND, util.getUID()));
		used.add(new Dodge(3, DIAMOND, util.getUID()));
		used.add(new Dodge(4, DIAMOND, util.getUID()));
		used.add(new Dodge(6, DIAMOND, util.getUID()));
		used.add(new Dodge(5, DIAMOND, util.getUID()));
		used.add(new Dodge(8, DIAMOND, util.getUID()));
		used.add(new Dodge(7, DIAMOND, util.getUID()));
		used.add(new Dodge(9, DIAMOND, util.getUID()));
		used.add(new Dodge(10, DIAMOND, util.getUID()));
		used.add(new Dodge(11, DIAMOND, util.getUID()));
		used.add(new Dodge(11, DIAMOND, util.getUID()));
		used.add(new Dodge(13, HEART, util.getUID()));
	}

	private void initPeach() {
		used.add(new Peach(3, HEART, util.getUID()));
		used.add(new Peach(4, HEART, util.getUID()));
		used.add(new Peach(6, HEART, util.getUID()));
		used.add(new Peach(7, HEART, util.getUID()));
		used.add(new Peach(8, HEART, util.getUID()));
		used.add(new Peach(9, HEART, util.getUID()));
		used.add(new Peach(12, DIAMOND, util.getUID()));
		used.add(new Peach(12, HEART, util.getUID()));
	}

	private void initInstant() {
		used.add(new Duel(1, SPADE, util.getUID()));
		used.add(new Duel(1, CLUB, util.getUID()));
		used.add(new Duel(1, DIAMOND, util.getUID()));
		// used.add(new ArrowSalvo(1, HEART, util.getUID()));
		// used.add(new Brotherhood(1, HEART, util.getUID()));
		// used.add(new Sabotage(3,SPADE, util.getUID()));
		// used.add(new Sabotage(3,CLUB, util.getUID()));
		// used.add(new Sabotage(4,SPADE, util.getUID()));
		// used.add(new Sabotage(4,CLUB, util.getUID()));
		// used.add(new Sabotage(12,SPADE, util.getUID()));
		// used.add(new Sabotage(12,HEART, util.getUID()));
		//
		// used.add(new Steal(3,SPADE, util.getUID()));
		// used.add(new Steal(3,DIAMOND, util.getUID()));
		// used.add(new Steal(4,SPADE, util.getUID()));
		// used.add(new Steal(4,DIAMOND, util.getUID()));
		// used.add(new Steal(11,SPADE, util.getUID()));

		used.add(new Harvest(3, HEART, util.getUID()));
		used.add(new Harvest(4, HEART, util.getUID()));

		// used.add(new BarbarianInvasion(7,SPADE, util.getUID()));
		// used.add(new BarbarianInvasion(7,CLUB, util.getUID()));
		used.add(new BarbarianInvasion(13, SPADE, util.getUID()));

		used.add(new Creation(7, HEART, util.getUID()));
		used.add(new Creation(8, HEART, util.getUID()));
		used.add(new Creation(9, HEART, util.getUID()));
		used.add(new Creation(11, HEART, util.getUID()));

		// used.add(new BorrowSword(12,CLUB, util.getUID()));
		// used.add(new BorrowSword(13,CLUB, util.getUID()));

		used.add(new Neutralization(11, SPADE, util.getUID()));
		used.add(new Neutralization(12, CLUB, util.getUID()));
		used.add(new Neutralization(13, CLUB, util.getUID()));
	}

	private void initDelayed() {
		// used.add(new Relaxation(6,SPADE, util.getUID()));
		// used.add(new Relaxation(6,HEART, util.getUID()));
		// used.add(new Relaxation(6,CLUB, util.getUID()));
		// used.add(new Lightning(1, SPADE, util.getUID()));
	}

	private void initEquipment() {
		// used.add(new ChuKoNu(1, CLUB, util.getUID()));
		// used.add(new ChuKoNu(1, DIAMOND, util.getUID()));
		// used.add(new DoubleSword(2,SPADE, util.getUID()));
		// used.add(new KylinBow(5,HEART, util.getUID()));
		// used.add(new HeavyAxe(5,DIAMOND, util.getUID()));
		// used.add(new DragonBlade(5,SPADE, util.getUID()))
		// used.add(new IronSword(6,SPADE, util.getUID()));
		// used.add(new SerpentSpear(12,SPADE, util.getUID()));
		// used.add(new Halbert(12,DIAMOND, util.getUID()));

		// used.add(new TaichiFormation(2,CLUB, util.getUID()));
		// used.add(new TaichiFormation(2,SPADE, util.getUID()));
		used.add(new HorsePlus(5, SPADE, util.getUID(), "Flash"));
		used.add(new HorseMinus(5, HEART, util.getUID(), "RedHare"));
		used.add(new HorsePlus(5, CLUB, util.getUID(), "Dilu"));
		used.add(new HorseMinus(13, SPADE, util.getUID(), "DaYuan"));
		used.add(new HorsePlus(13, HEART, util.getUID(), "GoldenLightning"));
		used.add(new HorseMinus(13, DIAMOND, util.getUID(), "Purple"));
	}

	private void initOriginal() {
		initAttack();
		initDodge();
		initPeach();
		// initInstant();
		// initDelayed();
		initEquipment();
	}

	private void initEX() {
		used.add(new IcySword(2,SPADE, util.getUID()));
		used.add(new IronShield(2, CLUB, util.getUID()));
		// used.add(new Lightning(12,HEART, util.getUID()));
		used.add(new Neutralization(12, DIAMOND, util.getUID()));
	}

	private void initBasicBattle() {
		used.add(new Peach(2, DIAMOND, util.getUID()));
		used.add(new Peach(3, DIAMOND, util.getUID()));
		used.add(new Peach(5, HEART, util.getUID()));
		used.add(new Peach(6, HEART, util.getUID()));

		used.add(new Wine(3, SPADE, util.getUID()));
		used.add(new Wine(3, CLUB, util.getUID()));
		used.add(new Wine(9, SPADE, util.getUID()));
		used.add(new Wine(9, CLUB, util.getUID()));
		used.add(new Wine(9, DIAMOND, util.getUID()));

		used.add(new Attack(FIRE, 3, HEART, util.getUID()));
		used.add(new Attack(THUNDER, 4, SPADE, util.getUID()));
		used.add(new Attack(FIRE, 4, DIAMOND, util.getUID()));
		used.add(new Attack(THUNDER, 5, SPADE, util.getUID()));
		used.add(new Attack(THUNDER, 5, CLUB, util.getUID()));
		used.add(new Attack(FIRE, 5, DIAMOND, util.getUID()));
		used.add(new Attack(THUNDER, 6, SPADE, util.getUID()));
		used.add(new Attack(THUNDER, 6, CLUB, util.getUID()));
		used.add(new Attack(THUNDER, 7, SPADE, util.getUID()));
		used.add(new Attack(FIRE, 7, HEART, util.getUID()));
		used.add(new Attack(THUNDER, 7, CLUB, util.getUID()));
		used.add(new Attack(THUNDER, 8, SPADE, util.getUID()));
		used.add(new Attack(THUNDER, 8, CLUB, util.getUID()));
		used.add(new Attack(FIRE, 10, HEART, util.getUID()));

		used.add(new Dodge(6, DIAMOND, util.getUID()));
		used.add(new Dodge(7, DIAMOND, util.getUID()));
		used.add(new Dodge(8, HEART, util.getUID()));
		used.add(new Dodge(8, DIAMOND, util.getUID()));
		used.add(new Dodge(9, HEART, util.getUID()));
		used.add(new Dodge(10, DIAMOND, util.getUID()));
		used.add(new Dodge(11, HEART, util.getUID()));
		used.add(new Dodge(11, DIAMOND, util.getUID()));
		used.add(new Dodge(12, HEART, util.getUID()));
	}

	private void initSpecialBattle() {
		used.add(new FireAttack(2, HEART, util.getUID()));
		used.add(new FireAttack(4, HEART, util.getUID()));
		used.add(new FireAttack(12, DIAMOND, util.getUID()));
		used.add(new Neutralization(1, HEART, util.getUID()));
		used.add(new Neutralization(13, SPADE, util.getUID()));
		used.add(new Neutralization(13, HEART, util.getUID()));
		// used.add(new Chain(10,CLUB, util.getUID()));
		// used.add(new Chain(11,SPADE, util.getUID()));
		// used.add(new Chain(11,CLUB, util.getUID()));
		// used.add(new Chain(12,SPADE, util.getUID()));
		// used.add(new Chain(12,CLUB, util.getUID()));
		// used.add(new Chain(13,CLUB, util.getUID()));

		// used.add(new Starvation(4,CLUB, util.getUID()));
		// used.add(new Starvation(10,SPADE, util.getUID()));
	}

	private void initEquipmentBattle() {
		// used.add(new AncientNuggetFalchion(1,SPADE, util.getUID()));
		used.add(new SilverLion(1, CLUB, util.getUID()));
		// used.add(new FeatheredFan(1,DIAMOND, util.getUID()));
		used.add(new RattanArmor(2, SPADE, util.getUID()));
		used.add(new RattanArmor(2, CLUB, util.getUID()));
		used.add(new HorsePlus(13, DIAMOND, util.getUID(), "Hualiu"));
	}

	private void initBattle() {
		initBasicBattle();
		// initSpecialBattle();
		initEquipmentBattle();
	}

	private void initializeDeck(Set<DeckPack> packs) {
		initOriginal();
		if (packs.contains(DeckPack.EX))
			initEX();
		if (packs.contains(DeckPack.BATTLE))
			initBattle();
		resetDeck();
	}
}
