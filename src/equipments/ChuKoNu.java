package equipments;



public class ChuKoNu extends Weapon
{
	public ChuKoNu(int num, int suit)
	{
		super(1, num, suit);
	}

	@Override
	public String getName() 
	{
		return "Chu Ko Nu";
	}
}
