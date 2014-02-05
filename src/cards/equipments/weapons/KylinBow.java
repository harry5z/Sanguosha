package cards.equipments.weapons;

public class KylinBow extends Weapon
{

	public KylinBow(int num, int suit) 
	{
		super(5, num, suit);
	}

	@Override
	public String getName()
	{
		return "Kylin Bow";
	}

}
