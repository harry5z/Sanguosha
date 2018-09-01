package cards;

import java.io.Serializable;

import core.GameState;
import core.client.game.operations.Operation;

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
	private final int id; // unique id
	private final boolean isReal;

	/**
	 * Initialization of normal cards
	 * 
	 * @param number
	 * @param suit
	 * @param type
	 */
	public Card(int number, Suit suit, CardType type, int id) {
		this.number = number;
		this.type = type;
		this.suit = suit;
		if (suit == Suit.DIAMOND || suit == Suit.HEART) {
			color = Color.RED;
		} else {
			color = Color.BLACK;
		}
		this.id = id;
		this.isReal = true;
	}
	
	/**
	 * initialization of transformed cards (from skills, equipments, etc.)
	 */
	public Card(int number, Suit suit, CardType type) {
		this.number = number;
		this.type = type;
		this.suit = suit;
		if (suit == Suit.DIAMOND || suit == Suit.HEART) {
			color = Color.RED;
		} else {
			color = Color.BLACK;
		}
		this.id = -1;
		this.isReal = false;
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
		this.id = -1;
		isReal = false;
	}

	public abstract String getName();

	/**
	 * returns an operation corresponding to this card
	 * 
	 * @return the corresponding operation
	 */
	public abstract Operation generateOperation();

	/**
	 * decides whether the card is activatable by player during TURN_DEAL
	 * 
	 * @param player
	 * @return true if activatable, false if not
	 */
	public abstract boolean isActivatable(GameState game);

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
		if (!(obj instanceof Card)) {
			return false;
		}
		return isReal ? id == ((Card) obj).id : false;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}