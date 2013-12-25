package core;


public abstract class Basic extends Card
{
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
