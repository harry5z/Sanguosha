package cards.equipments.weapons;




public class ChuKoNu extends Weapon
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1620384786441396718L;

	public ChuKoNu(int num, Suit suit)
	{
		super(1, num, suit);
	}

	@Override
	public String getName() 
	{
		return "Chu Ko Nu";
	}
}
