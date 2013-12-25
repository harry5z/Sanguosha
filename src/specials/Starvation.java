package specials;

public class Starvation extends Delayed
{

	public Starvation(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Starvation";
	}

}
