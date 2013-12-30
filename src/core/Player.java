package core;

import java.util.ArrayList;

import update.*;
import listener.*;
import equipments.HorseMinus;
import equipments.HorsePlus;
import equipments.Shield;
import equipments.Weapon;

public abstract class Player
{
	//personal properties
	private String name;
	private int position;
	private Hero hero;
	
	//in-game properties
	private int healthCurrent;
	private int attackLimit;
	private int attackUsed;
	private int wineLimit;
	private int wineUsed;
	private boolean isWineUsed;
	
	private boolean flipped;
	private boolean isAlive;
	private boolean isDying;
	private boolean weaponEquipped;
	private boolean shieldEquipped;
	private boolean horsePlusEquipped;
	private boolean horseMinusEquipped;
	
	//other properties
	private ArrayList<Card> decisionArea;
	private Weapon weapon;
	private Shield shield;
	private HorsePlus horsePlus;
	private HorseMinus horseMinus;
	
	

	
	public Player(String name,int position)
	{
		
		//init personal properties
		this.name = name;
		this.position = position;
		//init in-game properties
		
		isAlive = true;
		isDying = false;
		weaponEquipped = false;
		shieldEquipped = false;
		horsePlusEquipped = false;
		horseMinusEquipped = false;
		attackLimit = 1;
		attackUsed = 0;
		wineLimit = 1;
		wineUsed = 0;
		isWineUsed = false;
		
		//init other properties
		decisionArea = new ArrayList<Card>();
		weapon = null;
		shield = null;
		horsePlus = null;
		horseMinus = null;
		

	}
	public PlayerInfo getPlayerInfo()
	{
		return new PlayerInfo(name,position);
	}
	/**
	 * upon setting a hero
	 * @param hero
	 */
	public void setHero(Hero hero)
	{
		this.hero = hero;
		healthCurrent = hero.getHealthLimit();
	}
	public Hero getHero()
	{
		return hero;
	}
	//**************** methods related to listeners *********************



	//************* methods related to flipping ***************
	public boolean isFlipped()
	{
		return flipped;
	}
	public void flip()
	{
		flipped = !flipped;
	}
	//**************** methods related to health *******************
	public boolean isDying()
	{
		return isDying;
	}
	public void setIsDying(boolean isDying)
	{
		this.isDying = isDying;
	}
	/**
	 * change of health limit
	 * <li>{@link HealthListener} notified
	 * @param n
	 */
	public void changeHealthLimitTo(int n)
	{
		hero.changeHealthLimitTo(n);
		if(healthCurrent > hero.getHealthLimit())
			changeHealthCurrentTo(hero.getHealthLimit());

	}
	/**
	 * change of health limit
	 * <li>{@link HealthListener} notified
	 * @param n
	 */
	public void changeHealthLimitBy(int n)
	{
		hero.changeHealthLimitBy(n);
		if(healthCurrent > hero.getHealthLimit())
			changeHealthCurrentTo(hero.getHealthLimit());
		
	}
	public int getHealthLimit()
	{
		return hero.getHealthLimit();
	}
	/**
	 * strict change of health (i.e. no damage, does not invoke skills).
	 * <li>{@link HealthListener} notified
	 * @param n
	 */
	public void changeHealthCurrentTo(int n)
	{
		healthCurrent = n;
		hero.changeCardLimitTo(healthCurrent);
		
	}

	public void changeHealthCurrentBy(int n)
	{
		healthCurrent += n;
		hero.changeCardLimitTo(healthCurrent);
		
	}
	public int getHealthCurrent()
	{
		return healthCurrent;
	}
	//**************** methods related to cards on hand ***************
	public int getCardOnHandLimit()
	{
		return hero.getCardOnHandLimit();
	}

	/**
	 * <li>{@link CardOnHandListener} notified
	 * @param card
	 */
	public abstract void addCard(Card card);
	/**
	 * <li>{@link CardOnHandListener} notified
	 * @param cards
	 */
	public void addCards(ArrayList<Card> cards)
	{
		for(Card card : cards)
			addCard(card);
	}
	/**
	 * <li>{@link CardOnHandListener} notified
	 * <li>{@link CardDisposalListener} notified
	 * @param card
	 */
	public abstract void useCard(Card card);
	/**
	 * <li>{@link CardOnHandListener} notified
	 * @param cards
	 */
	public void useCards(ArrayList<Card> cards)
	{
		for(Card card : cards)
			useCard(card);
	}
	/**
	 * <li>{@link CardDisposalListener} notified
	 * @param card
	 */
	public abstract void discardCard(Card card);
	/**
	 * <li>{@link CardDisposalListener} notified
	 * @param cards
	 */
	public void discardCards(ArrayList<Card> cards)
	{
		for(Card card : cards)
			discardCard(card);
	}
	public abstract int getCardsOnHandCount();
	//**************** methods related to equipments ******************
	public boolean isEquipped()
	{
		return weaponEquipped || shieldEquipped || 
				horsePlusEquipped || horseMinusEquipped;
	}
	public boolean isEquippedWeapon(){return weaponEquipped;}
	public boolean isEquippedShield(){return shieldEquipped;}
	public boolean isEquippedHorsePlus(){return horsePlusEquipped;}
	public boolean isEquippedHorseMinus(){return horseMinusEquipped;}
	public Shield getShield(){return shield;}
	public Weapon getWeapon(){return weapon;}
	/**
	 * discard an equipment
	 * <li>{@link EquipmentListener} notified
	 * <li>{@link CardDisposalListener} notified
	 * @param type
	 * @return the equipment discarded
	 */
	public Equipment unequip(int type)
	{
		Equipment temp = null;
		if(type == Equipment.WEAPON)
		{
			temp = weapon;
			weapon = null;
			weaponEquipped = false;
		}
		else if(type == Equipment.SHIELD)
		{
			temp = shield;
			shield = null;
			shieldEquipped = false;
		}
		else if(type == Equipment.HORSEPLUS)
		{
			temp = horsePlus;
			horsePlus = null;
			horsePlusEquipped = false;
		}
		else if(type == Equipment.HORSEMINUS)
		{
			temp = horseMinus;
			horseMinus = null;
			horseMinusEquipped = false;
		}
		//sendToMaster(new DisposalOfCards(this,temp));
		return temp;
	}
	/**
	 * equip new equipment, return the old one. Return null if nothing is replaced
	 * <li>{@link EquipmentListener} notified
	 * <li>{@link CardDisposalListener} notified
	 * @param equipment : new equipment
	 * @return old equipment, null if no old equipment
	 */
	public Equipment equip(Equipment equipment)
	{
		Equipment temp = null;
		if(equipment instanceof HorsePlus)
		{
			temp = unequip(Equipment.HORSEPLUS);
			horsePlus = (HorsePlus) equipment;
			horsePlusEquipped = true;
		}
		else if(equipment instanceof HorseMinus)
		{
			temp = unequip(Equipment.HORSEMINUS);
			horseMinus = (HorseMinus)equipment;
			horseMinusEquipped = true;
		}
		else if(equipment instanceof Weapon)
		{
			temp = unequip(Equipment.WEAPON);
			weapon = (Weapon)equipment;
			weaponEquipped = true;
		}
		else if(equipment instanceof Shield)
		{
			temp = unequip(Equipment.SHIELD);
			shield = (Shield)equipment;
			shieldEquipped = true;
		}

		return temp;
	}

	//************** methods related to range/distance *************
	/**
	 * Attack range, not distance
	 * @return attack range
	 */
	public int getAttackRange()
	{
		int range = 1;
		if(weaponEquipped)
			range += (weapon.getRange()-1);
		if(horseMinusEquipped)
			range++;
		return range;
	}
	/**
	 * Reach distance, excluding weapon range
	 * @return reach distance
	 */
	public int getReachDistance()
	{
		int distance = 1;
		if(horseMinusEquipped)
			distance++;
		return distance;
	}
	/**
	 * Distance to another player
	 * @param player
	 * @return a number that can be smaller than 1
	 */
	public int getDistanceTo(Player player,int numberOfPlayersAlive)
	{
		int counterclockwise = player.position - position;
		int clockwise = position+numberOfPlayersAlive-player.position;
		int shorter = counterclockwise >= clockwise ? clockwise : counterclockwise;
		if(isEquippedHorseMinus())
			shorter--;
		if(player.isEquippedHorsePlus())
			shorter++;
		return shorter;
	}
	/**
	 * check whether player is in the attack range
	 * @param player
	 * @return true on yes, false on no
	 */
	public boolean isPlayerInRange(Player player,int numberOfPlayersAlive)
	{
		return getDistanceTo(player,numberOfPlayersAlive) <= getAttackRange();
	}
	/**
	 * check whether player is in the reach distance
	 * @param player
	 * @return true on yes, false on no
	 */
	public boolean isPlayerInDistance(Player player, int numberOfPlayersAlive)
	{
		return getDistanceTo(player, numberOfPlayersAlive) <= getReachDistance();
	}
	//************** methods related to damage *******************
	public abstract void takeDamage(Damage damage);


	public int getPosition()
	{
		return position;
	}
	public String getName()
	{
		return name;
	}
	public void kill()
	{
		isAlive = false;
	}
	public boolean isAlive()
	{
		return isAlive;
	}
	public void setAttackLimit(int limit)
	{
		attackLimit = limit;
	}
	public void setAttackUsed(int amount)
	{
		attackUsed = amount;
	}
	public void useAttack()
	{
		attackUsed++;
	}
	public int getAttackUsed()
	{
		return attackUsed;
	}
	public int getAttackLimit()
	{
		return attackLimit;
	}
	public void setWineUsed(int amount)
	{
		wineUsed = amount;
	}
	public void useWine()
	{
		wineUsed++;
		isWineUsed = false;
		//in the future, notify gui
	}
	public boolean isWineUsed()
	{
		return isWineUsed;
	}
	public int getWineUsed()
	{
		return wineUsed;
	}
	public int getWineLimit()
	{
		return wineLimit;
	}
	public abstract void startDealing();
	public abstract void endDealing();
	public void endTurn()
	{
		attackUsed = 0;
		wineUsed = 0;
		isWineUsed = false;
	}
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Player))
			return false;
		else
			return position == (((Player)obj).position);
	}
	public boolean isEqualTo(PlayerInfo p)
	{
		return position == p.getPosition();
	}
}
