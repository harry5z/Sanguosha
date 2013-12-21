package data;

import core.*;

public class Damage extends Data
{
	private boolean isDamage;
	private int element;
	private int amount;
	private int[] cards;
	private Card damagedBy;
	private PlayerImpl source;
	public Damage(boolean isD, int elem, int amt, int[] cds, Card by, PlayerImpl src)
	{
		assert(isD);
		isDamage = isD;
		element = elem;
		amount = amt;
		cards = cds;
		damagedBy = by;
		source = src;
	}
	public Damage(boolean isD, int amt)
	{
		assert(!isD);
		isDamage = isD;
		amount = amt;
	}
	public int getElement(){return element;}
	public boolean isDamage(){return isDamage;}
	public int getAmount(){return amount;}
	public int[] getCards(){return cards;}
	public Card getDamagedBy(){return damagedBy;}
	public PlayerImpl getSource(){return source;}
}
