package specials;

public class Harvest extends Instant
{

	public Harvest(int num, int suit)
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Harvest";
	}

}
