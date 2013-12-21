package core;


public abstract class Special extends Card
{
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
