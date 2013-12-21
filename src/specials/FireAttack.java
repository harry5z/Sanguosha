package specials;

public class FireAttack extends Instant
{

	public FireAttack(int num, int suit)
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Fire Attack";
	}

}
