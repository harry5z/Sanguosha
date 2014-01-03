package core;

/**
 * The "special" type of cards, including Steal, Lightning, etc.
 * @author Harry
 *
 */
public abstract class Special extends Card
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1458323578635106662L;
	private boolean instant;
	public Special(int num, int suit, boolean isInstant)
	{
		super(num,suit,Card.SPECIAL);
		instant = isInstant;
	}
	public boolean isInstant()
	{
		return instant;
	}
}
