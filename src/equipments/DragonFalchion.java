package equipments;

public class DragonFalchion extends Weapon
{

	public DragonFalchion(int num, int suit) 
	{
		super(3, num, suit);
	}

	@Override
	public String getName() 
	{
		return "Dragon Falchion";
	}

}
