package equipments;

import core.Equipment;




public abstract class Weapon extends Equipment
{
	private int range;//1 - 5 currently
	public Weapon(int range, int num, int suit)
	{
		super(num, suit, Equipment.WEAPON);
		this.range = range;
	}
	public void setRange(int range)
	{
		this.range = range;
	}
	public int getRange()
	{
		return range;
	}
}
