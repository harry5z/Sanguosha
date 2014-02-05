package cards.basics;

import cards.Card;

/**
 * The "Basic" type of cards, consisting of Attack, Dodge, Peach, and Wine (Battle expansion)
 * @author Harry
 *
 */
public abstract class Basic extends Card
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4758707277276652122L;
	public Basic(int num, int suit)
	{
		super(num, suit, Card.BASIC);
	}
	public Basic(int color)
	{
		super(color,Card.BASIC);
	}
	public Basic()
	{
		super(Card.COLORLESS,Card.BASIC);
	}
}
