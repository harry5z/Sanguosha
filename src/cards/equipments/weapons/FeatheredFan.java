package cards.equipments.weapons;


public class FeatheredFan extends Weapon
{

	public FeatheredFan(int num, int suit) 
	{
		super(4, num, suit);
	}

	@Override
	public String getName()
	{
		return "FeatheredFan";
	}

}
