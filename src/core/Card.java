package core;

import player.PlayerOriginalClientComplete;
import update.Update;

public abstract class Card extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2570541960577206529L;
	public static final int DIAMOND = 1;
	public static final int CLUB = 2;
	public static final int HEART = 3;
	public static final int SPADE = 4;
	
	public static final int RED = 1;
	public static final int BLACK = 2;
	public static final int COLORLESS = 3;
	
	public static final int BASIC = 1;
	public static final int SPECIAL = 2;
	public static final int EQUIPMENT = 3;
	
	private int color;//1. Red 2.Black 3.No Color
	private int number;//1-13, note that A,J,Q,K are not numeric
	private int suit; //1.Diamond 2.Club 3.Heart 4.Spade
	private int type; //1.Basic 2.Special 3.Equipment
	
	/**
	 * Initialization of normal cards
	 * @param number
	 * @param suit
	 * @param type
	 */
	public Card(int number, int suit, int type)
	{
		this.number = number;
		this.type = type;
		this.suit = suit;
		if(suit == DIAMOND || suit == HEART)
			color = RED;
		else
			color = BLACK;
	}
	/**
	 * initialization of transformed cards
	 * @param type
	 * @param color
	 */
	public Card(int color,int type)
	{
		this.color = color;
		this.type = type;
	}
	public abstract String getName();
	public abstract void onActivatedBy(PlayerOriginalClientComplete player);
	public abstract boolean isActivatableBy(PlayerOriginalClientComplete player);
	public void onDeactivatedBy(PlayerOriginalClientComplete player)
	{
		player.getUpdateStack().pop();
	}
	public int getType()
	{
		return type;
	}
	public int getColor()
	{
		return color;
	}
	
	public int getNumber()
	{
		return number;
	}
	public int getSuit()
	{
		return suit;
	}
	@Override
	public void onCardSelected(Card card)
	{
		
	}
	@Override
	public int hashCode()
	{
		return getName().hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Card))
			return false;
		Card other = (Card)obj;
		return getName().equals(other.getName()) && number == other.number
				&& suit == other.suit;
	}
}