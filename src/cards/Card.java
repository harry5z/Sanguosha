package cards;

import java.io.Serializable;

import commands.Command;
import commands.operations.Operation;
import player.PlayerComplete;
import utils.UIDProvider;

public abstract class Card implements Serializable {

	private static final long serialVersionUID = 2570541960577206529L;
	
	public enum Suit {
		DIAMOND, CLUB, HEART, SPADE
	}

	public enum Color {
		RED, BLACK, COLORLESS
	}

	public enum CardType {
		BASIC, SPECIAL, EQUIPMENT
	}

	private Color color;// 1. Red 2.Black 3.No Color
	private int number;// 1-13, note that A,J,Q,K are not numeric
	private Suit suit; // 1.Diamond 2.Club 3.Heart 4.Spade
	private CardType type; // 1.Basic 2.Special 3.Equipment
	private int id; // unique id
	private boolean isReal;

	/**
	 * Initialization of normal cards
	 * 
	 * @param number
	 * @param suit
	 * @param type
	 */
	public Card(int number, Suit suit, CardType type) {
		this.number = number;
		this.type = type;
		this.suit = suit;
		if (suit == Suit.DIAMOND || suit == Suit.HEART)
			color = Color.RED;
		else
			color = Color.BLACK;
		id = UIDProvider.getUID();
		isReal = true;
	}

	/**
	 * initialization of transformed cards (from skills, equipments, etc.)
	 * 
	 * @param type
	 * @param color
	 */
	public Card(Color color, CardType type) {
		this.color = color;
		this.type = type;
		isReal = false;
	}

	public abstract String getName();

	/**
	 * returns an operation corresponding to this card, with the next update set
	 * to be "next"
	 * 
	 * @param player
	 * @param next
	 * @return the corresponding operation
	 */
	public abstract Operation onActivatedBy(PlayerComplete player, Command next);

	/**
	 * decides whether the card is activatable by player during TURN_DEAL
	 * 
	 * @param player
	 * @return true if activatable, false if not
	 */
	public abstract boolean isActivatableBy(PlayerComplete player);

	public CardType getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

	public int getNumber() {
		return number;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Card))
			return false;
		Card other = (Card) obj;
		return isReal ? id == other.id : false;
	}
}