package equipments;

public class CoupleSword extends Weapon
{

	public CoupleSword(int num, int suit) 
	{
		super(2, num, suit);
	}

	@Override
	public String getName() 
	{
		return "Couple Sword";
	}

}
