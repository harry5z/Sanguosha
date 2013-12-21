package core;


import data.*;
import equipments.*;
import basics.*;
import specials.*;



public abstract class Hero 
{
	public static final int WEI = 1;
	public static final int SHU = 2;
	public static final int WU = 3;
	public static final int QUN = 4;
	

	public static final int MALE = 1;
	public static final int FEMALE = 2;
	
	private String name;
	private int force;
	private int lifeLimit;
	private int sex;
	
	private int lifeCurrent;
	private int cardLimit;
	private int cardCurrent;
	private boolean flipped;

	
	private Weapon weapon;
	private Shield shield;
	private HorsePlus horsePlus;
	private HorseMinus horseMinus;
	private int range;//attack range
	private int distance;//distance to others
	private int fromOthers;
	
	private boolean weaponEquipped;
	private boolean shieldEquipped;
	private boolean horsePlusEquipped;
	private boolean horseMinusEquipped;
	
	private boolean haveSkills;
	
	public Hero(int lifeLimit, int force, int sex, String name)
	{
		this.name = name;
		this.force = force;
		this.sex = sex;
		this.lifeLimit = lifeLimit;
		lifeCurrent = lifeLimit;
		cardLimit = lifeLimit;
		cardCurrent = lifeLimit;
		flipped = false;

		range = 1;
		distance = 1;
		fromOthers = 1;
		weaponEquipped = false;
		shieldEquipped = false;
		horsePlusEquipped = false;
		horseMinusEquipped = false;
		weapon = null;
		shield = null;
		horsePlus = null;
		horseMinus = null;
		haveSkills = true;
	}
	public void loseSkills(){haveSkills = false;}
	public boolean haveSkills(){return haveSkills;}
	public void setName(String n){name = n;}
	public String getName(){return name;}
	public void changeHealthLimit(int n){lifeLimit = n;}
	public void changeHealthLimitBy(int n){lifeLimit += n;}
	public int getHealthLimit(){return lifeLimit;}
	public void changeHealthCurrent(int n){lifeCurrent = n;}
	public void changeHealthCurrentBy(int n){lifeCurrent += n;}
	public int getHealthCurrent(){return lifeCurrent;}
	public void changeCardLimit(int n){cardLimit = n;}
	public void changeCardLimitBy(int n){cardLimit += n;}
	public int getCardLimit(){return cardLimit;}
	public void changeCardCurrent(int n){cardCurrent = n;}
	public void changeCardCurrentBy(int n){cardCurrent += n;}
	public int getCardCurrent(){return cardCurrent;}
	public void changeSex(int n){sex = n;}
	public int getSex(){return sex;}
	public void changeForce(int n){force = n;}
	public int getForce(){return force;}
	public boolean isEquipped()
	{
		return isEquippedWeapon() || isEquippedShield() || 
		isEquippedHorsePlus() || isEquippedHorseMinus();
	}
	public boolean isEquippedWeapon(){return weaponEquipped;}
	public boolean isEquippedShield(){return shieldEquipped;}
	public boolean isEquippedHorsePlus(){return horsePlusEquipped;}
	public boolean isEquippedHorseMinus(){return horseMinusEquipped;}
	public Equipment deEquip(int type,Equipment replace)
	{
		assert isEquipped();
		Equipment temp = null;
		if(type == Equipment.WEAPON)
		{
			temp = weapon;
			weapon = (Weapon)replace;
		}
		else if(type == Equipment.SHIELD)
		{
			temp = shield;
			shield = (Shield)replace;
		}
		else if(type == Equipment.HORSEPLUS)
		{
			temp = horsePlus;
			horsePlus = (HorsePlus)replace;
		}
		else if(type == Equipment.HORSEMINUS)
		{
			temp = horseMinus;
			horseMinus = (HorseMinus)replace;
		}
		return temp;
	}
	public Equipment equip(Equipment e)
	{
		assert e != null;
		Equipment temp = null;
		if(e instanceof HorsePlus)
		{
			if(isEquippedHorsePlus())
				temp = deEquip(Equipment.HORSEPLUS,e);
			else
			{
				horsePlus = (HorsePlus) e;
				fromOthers++;
				horsePlusEquipped = true;
			}
		}
		else if(e instanceof HorseMinus)
		{
			if(isEquippedHorseMinus())
				temp = deEquip(Equipment.HORSEMINUS,e);
			else
			{
				range++;
				horseMinus = (HorseMinus)e;
				horseMinusEquipped = true;
			}
		}
		else if(e instanceof Weapon)
		{
			if(isEquippedWeapon())
				temp = deEquip(Equipment.WEAPON,e);
			else
			{
				weapon = (Weapon)e;
				range += weapon.getRange();
				weaponEquipped = true;
			}
		}
		else if(e instanceof Shield)
		{
			if(isEquippedShield())
				temp = deEquip(Equipment.SHIELD,e);
			else
			{
				shield = (Shield)e;
				shieldEquipped = true;
			}
		}
		return temp;
	}
	public void targetedBy(Hero who, Weapon w, Card c)
	{
//		if(c instanceof Kill)
//			targetedByKill((Kill)c);
//		else if(c instanceof Special)
//			isNeutralized(1);
//		else if(c instanceof Duel  || c instanceof BarbarianInvasion)
//			requireKill(1);
	}

	public void takeDamage(Damage d)
	{
		
	}
	public void requireDodge(int amount)
	{
	
	}
	public void requireKill(int amount){}
//	public boolean isNeutralized(int amount)
//	{
//		for(Card k : cards)
//			if(k instanceof Neutralization)
//				return cardUsed(k);
//		return false;
//	}
//	public boolean cardUsed(Card c)
//	{
//		
//		cards.remove(c);
//		return false;
//	}
	
	
	
	
	
	
	
}
