package equipments;

public class IronSword extends Weapon
{

	public IronSword(int num, int suit)
	{
		super(2, num, suit);
	}

	@Override
	public String getName() 
	{
		return "IronSword";
	}

}
