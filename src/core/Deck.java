package core;

import java.util.ArrayList;
import java.util.Random;

import specials.ArrowSalvo;
import specials.BarbarianInvasion;
import specials.BorrowSword;
import specials.Brotherhood;
import specials.Chain;
import specials.Creation;
import specials.CutSupply;
import specials.Destroy;
import specials.Duel;
import specials.FireAttack;
import specials.Harvest;
import specials.Lightning;
import specials.Pleasure;
import specials.Neutralization;
import specials.Steal;
import basics.Attack;
import basics.Dodge;
import basics.Peach;
import basics.Wine;
import equipments.AncientNuggetFalchion;
import equipments.ChuKoNu;
import equipments.CoupleSword;
import equipments.DragonFalchion;
import equipments.Halbert;
import equipments.HeavyAxe;
import equipments.HorseMinus;
import equipments.HorsePlus;
import equipments.IronShield;
import equipments.IronSword;
import equipments.KylinBow;
import equipments.LongSpear;
import equipments.PhoenixFeatherFan;
import equipments.RattenArmor;
import equipments.SilverLion;
import equipments.IcySword;
import equipments.TaichiFormation;

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
	public int getDeckSize()
	{
		return deck.size();
	}
	public void discard(Card c)
	{
		used.add(c);
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
		used.add(new Duel(1, spade));
		used.add(new Lightning(1, spade));
		used.add(new ArrowSalvo(1, heart));
		used.add(new Brotherhood(1, heart));
		used.add(new Duel(1, club));
		used.add(new ChuKoNu(1, club));
		used.add(new Duel(1, diamond));
		used.add(new ChuKoNu(1, diamond));
		used.add(new TaichiFormation(2,spade));
		used.add(new CoupleSword(2,spade));
		used.add(new Dodge(2,heart));
		used.add(new Dodge(2,heart));
		used.add(new Attack(normal,2,club));
		used.add(new TaichiFormation(2,club));
		used.add(new Dodge(2,diamond));
		used.add(new Dodge(2,diamond));
		used.add(new Destroy(3,spade));
		used.add(new Steal(3,spade));
		used.add(new Peach(3,heart));
		used.add(new Harvest(3,heart));
		used.add(new Attack(normal,3,club));
		used.add(new Destroy(3,club));
		used.add(new Dodge(3,diamond));
		used.add(new Steal(3,diamond));
		used.add(new Destroy(4,spade));
		used.add(new Steal(4,spade));
		used.add(new Peach(4,heart));
		used.add(new Harvest(4,heart));
		used.add(new Attack(normal,4,club));
		used.add(new Destroy(4,club));
		used.add(new Dodge(4,diamond));
		used.add(new Steal(4,diamond));
		used.add(new DragonFalchion(5,spade));
		used.add(new HorsePlus(5,spade,"Flash"));
		used.add(new KylinBow(5,heart));
		used.add(new HorseMinus(5,heart,"RedHare"));
		used.add(new Attack(normal,5,club));
		used.add(new HorsePlus(5,club,"Dilu"));
		used.add(new Dodge(5,diamond));
		used.add(new HeavyAxe(5,diamond));
		used.add(new Pleasure(6,spade));
		used.add(new IronSword(6,spade));
		used.add(new Peach(6,heart));
		used.add(new Pleasure(6,heart));
		used.add(new Attack(normal,6,club));
		used.add(new Pleasure(6,club));
		used.add(new Attack(normal,6,diamond));
		used.add(new Dodge(6,diamond));
		used.add(new Attack(normal,7,spade));
		used.add(new BarbarianInvasion(7,spade));
		used.add(new Peach(7,heart));
		used.add(new Creation(7,heart));
		used.add(new Attack(normal,7,club));
		used.add(new BarbarianInvasion(7,club));
		used.add(new Attack(normal,7,diamond));
		used.add(new Dodge(7,diamond));
		used.add(new Attack(normal,8,spade));
		used.add(new Attack(normal,8,spade));
		used.add(new Peach(8,heart));
		used.add(new Creation(8,heart));
		used.add(new Attack(normal,8,club));
		used.add(new Attack(normal,8,club));
		used.add(new Attack(normal,8,diamond));
		used.add(new Dodge(8,diamond));
		used.add(new Attack(normal,9,spade));
		used.add(new Attack(normal,9,spade));
		used.add(new Peach(9,heart));
		used.add(new Creation(9,heart));
		used.add(new Attack(normal,9,club));
		used.add(new Attack(normal,9,club));
		used.add(new Attack(normal,9,diamond));
		used.add(new Dodge(9,diamond));
		used.add(new Attack(normal,10,spade));
		used.add(new Attack(normal,10,spade));
		used.add(new Attack(normal,10,heart));
		used.add(new Attack(normal,10,heart));
		used.add(new Attack(normal,10,club));
		used.add(new Attack(normal,10,club));
		used.add(new Attack(normal,10,diamond));
		used.add(new Dodge(10,diamond));
		used.add(new Steal(11,spade));
		used.add(new Neutralization(11,spade));
		used.add(new Attack(normal,11,heart));
		used.add(new Creation(11,heart));
		used.add(new Attack(normal,11,club));
		used.add(new Attack(normal,11,club));
		used.add(new Dodge(11,diamond));
		used.add(new Dodge(11,diamond));
		used.add(new Destroy(12,spade));
		used.add(new LongSpear(12,spade));
		used.add(new Peach(12,heart));
		used.add(new Destroy(12,heart));
		used.add(new BorrowSword(12,club));
		used.add(new Neutralization(12,club));
		used.add(new Peach(12,diamond));
		used.add(new Halbert(12,diamond));
		used.add(new BarbarianInvasion(13,spade));
		used.add(new HorseMinus(13,spade,"DaWan"));
		used.add(new Dodge(13,heart));
		used.add(new HorsePlus(13,heart,"YellowHooves"));
		used.add(new BorrowSword(13,club));
		used.add(new Neutralization(13,club));
		used.add(new Attack(normal,13,diamond));
		used.add(new HorseMinus(13,diamond,"Purple"));
		if(useEX)
		{
			used.add(new IcySword(2,spade));
			used.add(new IronShield(2,club));
			used.add(new Lightning(12,heart));
			used.add(new Neutralization(12,diamond));
		}
		if(useBATTLE)
		{
			used.add(new AncientNuggetFalchion(1,spade));
			used.add(new Neutralization(1,heart));
			used.add(new SilverLion(1,club));
			used.add(new PhoenixFeatherFan(1,diamond));
			used.add(new RattenArmor(2,spade));
			used.add(new FireAttack(2,heart));
			used.add(new RattenArmor(2,club));
			used.add(new Peach(2,diamond));
			used.add(new Wine(3,spade));
			used.add(new Attack(fire,3,heart));
			used.add(new Wine(3,club));
			used.add(new Peach(3,diamond));
			used.add(new Attack(thunder,4,spade));
			used.add(new FireAttack(4,heart));
			used.add(new CutSupply(4,club));
			used.add(new Attack(fire,4,diamond));
			used.add(new Attack(thunder,5,spade));
			used.add(new Peach(5,heart));
			used.add(new Attack(thunder,5,club));
			used.add(new Attack(fire,5,diamond));
			used.add(new Attack(thunder,6,spade));
			used.add(new Peach(6,heart));
			used.add(new Attack(thunder,6,club));
			used.add(new Dodge(6,diamond));
			used.add(new Attack(thunder,7,spade));
			used.add(new Attack(fire,7,heart));
			used.add(new Attack(thunder,7,club));
			used.add(new Dodge(7,diamond));
			used.add(new Attack(thunder,8,spade));
			used.add(new Dodge(8,heart));
			used.add(new Attack(thunder,8,club));
			used.add(new Dodge(8,diamond));
			used.add(new Wine(9,spade));
			used.add(new Dodge(9,heart));
			used.add(new Wine(9,club));
			used.add(new Wine(9,diamond));
			used.add(new CutSupply(10,spade));
			used.add(new Attack(fire,10,heart));
			used.add(new Chain(10,club));
			used.add(new Dodge(10,diamond));
			used.add(new Chain(11,spade));
			used.add(new Dodge(11,heart));
			used.add(new Chain(11,club));
			used.add(new Dodge(11,diamond));
			used.add(new Chain(12,spade));
			used.add(new Dodge(12,heart));
			used.add(new Chain(12,club));
			used.add(new FireAttack(12,diamond));
			used.add(new Neutralization(13,spade));
			used.add(new Neutralization(13,heart));
			used.add(new Chain(13,club));
			used.add(new HorsePlus(13,diamond,"..."));
		}
		resetDeck();
	}
}
