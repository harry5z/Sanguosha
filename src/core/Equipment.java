package core;



public abstract class Equipment extends Card
{
	private int equipmentType;//1.Weapon 2.Shield 3.Horse+ 4.Horse-
	private boolean equipped;
	public static final int WEAPON = 1;
	public static final int SHIELD = 2;
	public static final int HORSEPLUS = 3;
	public static final int HORSEMINUS = 4;

	public Equipment(int num, int suit,int equipmentType)
	{
		super(num, suit, Card.EQUIPMENT);
		this.equipped = false;
		this.equipmentType = equipmentType;
	}
	public void setEquipped(boolean b)
	{
		equipped = b;
	}
	public boolean isEquipped()
	{
		return equipped;
	}
	public int getEquipmentType()
	{
		return equipmentType;
	}
}
