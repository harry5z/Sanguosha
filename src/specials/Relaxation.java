package specials;

public class Relaxation extends Delayed
{

	public Relaxation(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Relaxation";
	}

}
