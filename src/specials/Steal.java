package specials;

public class Steal extends Instant
{

	public Steal(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Steal";
	}

}
