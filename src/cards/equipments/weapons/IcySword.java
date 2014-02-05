package equipments.weapons;

public class IcySword extends Weapon
{

	public IcySword(int num, int suit) 
	{
		super(2, num, suit);
	}

	@Override
	public String getName() 
	{
		return "Icy Sword";
	}

}
