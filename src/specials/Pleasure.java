package specials;

public class Pleasure extends Delayed
{

	public Pleasure(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Pleasure";
	}

}
