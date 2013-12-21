package equipments;

import core.Equipment;




public class HorsePlus extends Equipment
{
	private String name;
	public HorsePlus(int num, int suit, String name) 
	{
		super(num, suit, Equipment.HORSEPLUS);
		this.name = name;
	}
	public int getDistance()
	{
		return 1;
	}
	@Override
	public String getName()
	{
		return name;
	}
}
