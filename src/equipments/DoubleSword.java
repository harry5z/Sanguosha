package equipments;

public class DoubleSword extends Weapon
{

	public DoubleSword(int num, int suit) 
	{
		super(2, num, suit);
	}

	@Override
	public String getName() 
	{
		return "DoubleSword";
	}

}
