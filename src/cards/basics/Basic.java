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
	public Basic(int num, Suit suit)
	{
		super(num, suit, CardType.BASIC);
	}
	public Basic(Color color)
	{
		super(color,CardType.BASIC);
	}
	public Basic()
	{
		super(Color.COLORLESS,CardType.BASIC);
	}
}
