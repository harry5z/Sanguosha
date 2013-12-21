package specials;

public class Lightning extends Delayed
{
	public Lightning(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Lightning";
	}

}
