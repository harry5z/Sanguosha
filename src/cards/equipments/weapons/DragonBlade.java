package cards.equipments.weapons;


public class DragonBlade extends Weapon
{

	public DragonBlade(int num, int suit) 
	{
		super(3, num, suit);
	}

	@Override
	public String getName() 
	{
		return "DragonBlade";
	}

}
