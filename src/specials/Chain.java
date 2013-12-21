package specials;

public class Chain extends Instant
{

	public Chain(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Chain";
	}

}
