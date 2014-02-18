package cards.equipments.weapons;

import cards.equipments.Equipment;

public abstract class Weapon extends Equipment
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1252213293560974414L;
	private int range;//1 - 5 currently
	public Weapon(int range, int num, Suit suit)
	{
		super(num, suit, EquipmentType.WEAPON);
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
