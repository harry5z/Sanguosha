package specials;

public class CutSupply extends Delayed
{

	public CutSupply(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Cut Supply";
	}

}
