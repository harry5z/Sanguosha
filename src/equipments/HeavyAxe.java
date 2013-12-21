package equipments;

public class HeavyAxe extends Weapon
{
	public HeavyAxe(int num, int suit) 
	{
		super(3, num, suit);
	}

	@Override
	public String getName()
	{
		return "Heave Axe";
	}
	
}
