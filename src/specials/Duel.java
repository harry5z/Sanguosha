package specials;



public class Duel extends Instant
{

	public Duel(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Duel";
	}

}
