package core;

import java.util.ArrayList;
import java.util.Random;

import specials.*;
import basics.*;
import equipments.*;

public class Deck 
{
	private ArrayList<Card> used;
	private ArrayList<Card> deck;
	private Random random;
	
	public Deck(boolean useEx, boolean useBattle)
	{
		random = new Random();
		initializeDeck(useEx,useBattle);
	}
	public Card draw()
	{
		Card c = deck.remove(deck.size()-1);
		if(deck.size() == 0)
			resetDeck();
		return c;
	}
	public ArrayList<Card> drawMany(int amount)
	{
		ArrayList<Card> cards = new ArrayList<Card>(amount);
		for(int i = 0;i < amount;i++)
			cards.add(draw());
		return cards;
	}
	public int getDeckSize()
	{
		return deck.size();
	}
	public void discard(Card card)
	{
		used.add(card);
	}
	public void discardAll(ArrayList<Card> cards)
	{
		used.addAll(cards);
	}
	private void resetDeck()
	{
		while(used.size() != 0)
			deck.add(used.remove(random.nextInt(used.size())));
	}
	private void initializeDeck(boolean useEX, boolean useBATTLE)
	{
		used = new ArrayList<Card>();
		deck = new ArrayList<Card>();
		int diamond = Card.DIAMOND;
		int club = Card.CLUB;
		int heart = Card.HEART;
		int spade = Card.SPADE;
		int normal = Attack.NORMAL;
		int thunder = Attack.THUNDER;
		int fire = Attack.FIRE;
//		used.add(new Duel(1, spade));
//		used.add(new Lightning(1, spade));
//		used.add(new ArrowSalvo(1, heart));
//		used.add(new Brotherhood(1, heart));
//		used.add(new Duel(1, club));
//		used.add(new ChuKoNu(1, club));
//		used.add(new Duel(1, diamond));
//		used.add(new ChuKoNu(1, diamond));
//		used.add(new TaichiFormation(2,spade));
//		used.add(new DoubleSword(2,spade));
		used.add(new Dodge(2,heart));
		used.add(new Dodge(2,heart));
		used.add(new Attack(normal,2,club));
//		used.add(new TaichiFormation(2,club));
		used.add(new Dodge(2,diamond));
		used.add(new Dodge(2,diamond));
//		used.add(new Destroy(3,spade));
//		used.add(new Steal(3,spade));
		used.add(new Peach(3,heart));
//		used.add(new Harvest(3,heart));
		used.add(new Attack(normal,3,club));
//		used.add(new Destroy(3,club));
		used.add(new Dodge(3,diamond));
//		used.add(new Steal(3,diamond));
//		used.add(new Destroy(4,spade));
//		used.add(new Steal(4,spade));
		used.add(new Peach(4,heart));
//		used.add(new Harvest(4,heart));
		used.add(new Attack(normal,4,club));
//		used.add(new Destroy(4,club));
		used.add(new Dodge(4,diamond));
//		used.add(new Steal(4,diamond));
//		used.add(new DragonBlade(5,spade));
//		used.add(new HorsePlus(5,spade,"Flash"));
//		used.add(new KylinBow(5,heart));
//		used.add(new HorseMinus(5,heart,"RedHare"));
		used.add(new Attack(normal,5,club));
//		used.add(new HorsePlus(5,club,"Dilu"));
		used.add(new Dodge(5,diamond));
//		used.add(new HeavyAxe(5,diamond));
//		used.add(new Relaxation(6,spade));
//		used.add(new IronSword(6,spade));
		used.add(new Peach(6,heart));
//		used.add(new Relaxation(6,heart));
		used.add(new Attack(normal,6,club));
//		used.add(new Relaxation(6,club));
		used.add(new Attack(normal,6,diamond));
		used.add(new Dodge(6,diamond));
		used.add(new Attack(normal,7,spade));
//		used.add(new BarbarianInvasion(7,spade));
		used.add(new Peach(7,heart));
//		used.add(new Creation(7,heart));
		used.add(new Attack(normal,7,club));
//		used.add(new BarbarianInvasion(7,club));
		used.add(new Attack(normal,7,diamond));
		used.add(new Dodge(7,diamond));
		used.add(new Attack(normal,8,spade));
		used.add(new Attack(normal,8,spade));
		used.add(new Peach(8,heart));
//		used.add(new Creation(8,heart));
		used.add(new Attack(normal,8,club));
		used.add(new Attack(normal,8,club));
		used.add(new Attack(normal,8,diamond));
		used.add(new Dodge(8,diamond));
		used.add(new Attack(normal,9,spade));
		used.add(new Attack(normal,9,spade));
		used.add(new Peach(9,heart));
//		used.add(new Creation(9,heart));
		used.add(new Attack(normal,9,club));
		used.add(new Attack(normal,9,club));
		used.add(new Attack(normal,9,diamond));
//		used.add(new Dodge(9,diamond));
		used.add(new Attack(normal,10,spade));
		used.add(new Attack(normal,10,spade));
		used.add(new Attack(normal,10,heart));
		used.add(new Attack(normal,10,heart));
		used.add(new Attack(normal,10,club));
		used.add(new Attack(normal,10,club));
		used.add(new Attack(normal,10,diamond));
		used.add(new Dodge(10,diamond));
//		used.add(new Steal(11,spade));
//		used.add(new Neutralization(11,spade));
		used.add(new Attack(normal,11,heart));
//		used.add(new Creation(11,heart));
		used.add(new Attack(normal,11,club));
		used.add(new Attack(normal,11,club));
		used.add(new Dodge(11,diamond));
		used.add(new Dodge(11,diamond));
//		used.add(new Destroy(12,spade));
//		used.add(new SerpentSpear(12,spade));
		used.add(new Peach(12,heart));
//		used.add(new Destroy(12,heart));
//		used.add(new BorrowSword(12,club));
//		used.add(new Neutralization(12,club));
		used.add(new Peach(12,diamond));
//		used.add(new Halbert(12,diamond));
//		used.add(new BarbarianInvasion(13,spade));
//		used.add(new HorseMinus(13,spade,"DaWan"));
		used.add(new Dodge(13,heart));
//		used.add(new HorsePlus(13,heart,"YellowHooves"));
//		used.add(new BorrowSword(13,club));
//		used.add(new Neutralization(13,club));
		used.add(new Attack(normal,13,diamond));
//		used.add(new HorseMinus(13,diamond,"Purple"));
//		if(useEX)
//		{
//			used.add(new IcySword(2,spade));
//			used.add(new IronShield(2,club));
//			used.add(new Lightning(12,heart));
//			used.add(new Neutralization(12,diamond));
//		}
//		if(useBATTLE)
//		{
//			used.add(new AncientNuggetFalchion(1,spade));
//			used.add(new Neutralization(1,heart));
//			used.add(new SilverLion(1,club));
//			used.add(new FeatheredFan(1,diamond));
//			used.add(new RattenArmor(2,spade));
//			used.add(new FireAttack(2,heart));
//			used.add(new RattenArmor(2,club));
//			used.add(new Peach(2,diamond));
//			used.add(new Wine(3,spade));
//			used.add(new Attack(fire,3,heart));
//			used.add(new Wine(3,club));
//			used.add(new Peach(3,diamond));
//			used.add(new Attack(thunder,4,spade));
//			used.add(new FireAttack(4,heart));
//			used.add(new Starvation(4,club));
//			used.add(new Attack(fire,4,diamond));
//			used.add(new Attack(thunder,5,spade));
//			used.add(new Peach(5,heart));
//			used.add(new Attack(thunder,5,club));
//			used.add(new Attack(fire,5,diamond));
//			used.add(new Attack(thunder,6,spade));
//			used.add(new Peach(6,heart));
//			used.add(new Attack(thunder,6,club));
//			used.add(new Dodge(6,diamond));
//			used.add(new Attack(thunder,7,spade));
//			used.add(new Attack(fire,7,heart));
//			used.add(new Attack(thunder,7,club));
//			used.add(new Dodge(7,diamond));
//			used.add(new Attack(thunder,8,spade));
//			used.add(new Dodge(8,heart));
//			used.add(new Attack(thunder,8,club));
//			used.add(new Dodge(8,diamond));
//			used.add(new Wine(9,spade));
//			used.add(new Dodge(9,heart));
//			used.add(new Wine(9,club));
//			used.add(new Wine(9,diamond));
//			used.add(new Starvation(10,spade));
//			used.add(new Attack(fire,10,heart));
//			used.add(new Chain(10,club));
//			used.add(new Dodge(10,diamond));
//			used.add(new Chain(11,spade));
//			used.add(new Dodge(11,heart));
//			used.add(new Chain(11,club));
//			used.add(new Dodge(11,diamond));
//			used.add(new Chain(12,spade));
//			used.add(new Dodge(12,heart));
//			used.add(new Chain(12,club));
//			used.add(new FireAttack(12,diamond));
//			used.add(new Neutralization(13,spade));
//			used.add(new Neutralization(13,heart));
//			used.add(new Chain(13,club));
//			used.add(new HorsePlus(13,diamond,"..."));
//		}
		resetDeck();
	}
}
