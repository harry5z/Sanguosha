package specials;

public class Creation extends Instant
{

	public Creation(int num, int suit)
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Creation";
	}

}
