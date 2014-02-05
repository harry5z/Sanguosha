package cards;

import java.io.Serializable;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;

public abstract class Card implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2570541960577206529L;
	private static int uniqueID = 1;
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
	private int id;
	private boolean isReal;
	
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
		id = uniqueID;
		uniqueID++;
		isReal = true;
	}
	/**
	 * initialization of transformed cards (from skills, equipments, etc.)
	 * @param type
	 * @param color
	 */
	public Card(int color,int type)
	{
		this.color = color;
		this.type = type;
		isReal = false;
	}
	public abstract String getName();
	/**
	 * returns an operation corresponding to this card, with the next update set to be "next"
	 * @param player
	 * @param next
	 * @return the corresponding operation
	 */
	public abstract Operation onActivatedBy(PlayerOriginalClientComplete player, Update next);
	/**
	 * decides whether the card is activatable by player during TURN_DEAL
	 * @param player
	 * @return true if activatable, false if not
	 */
	public abstract boolean isActivatableBy(PlayerOriginalClientComplete player);

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
		return isReal ? id == other.id : false;
	}
}