package equipments;

public class Halbert extends Weapon
{

	public Halbert(int num, int suit) 
	{
		super(4, num, suit);
	}

	@Override
	public String getName() 
	{
		return "Halbert";
	}

}
