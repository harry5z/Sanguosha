package core;

import java.util.ArrayList;
import update.*;
import listener.*;
import equipments.HorseMinus;
import equipments.HorsePlus;
import equipments.Shield;
import equipments.Weapon;

/**
 * The player class, defines common traits of different implementations of players
 * @author Harry
 *
 */
public abstract class Player
{
	//********* personal properties *********
	private String name;
	private int position;
	private Hero hero;
	
	//********* in-game properties ***********
	private int healthCurrent;

	
	private boolean flipped;//whether player is flipped (not implemented yet)
	private boolean isAlive;//whether player is alive
	private boolean isDying;//whether player is in the near-death stage
	private boolean weaponEquipped;
	private boolean shieldEquipped;
	private boolean horsePlusEquipped;
	private boolean horseMinusEquipped;
	
	//********* cards other than cardsOnHand, public to all other players *********
	private ArrayList<Card> decisionArea;//not implemented yet
	private Weapon weapon;
	private Shield shield;
	private HorsePlus horsePlus;
	private HorseMinus horseMinus;
	
	public Player(String name)
	{
		//init personal properties
		this.name = name;
		init();
	}
	public Player(String name, int position)
	{
		this.name = name;
		this.position = position;
		init();
	}
	private void init()
	{
		//init in-game properties
		isAlive = true;
		isDying = false;
		weaponEquipped = false;
		shieldEquipped = false;
		horsePlusEquipped = false;
		horseMinusEquipped = false;

		
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
	/**
	 * use this very carefully, usually only at game start
	 * @param position
	 */
	public void setPosition(int position)
	{
		this.position = position;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Hero getHero()
	{
		return hero;
	}
	public int getPosition()
	{
		return position;
	}
	public String getName()
	{
		return name;
	}

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
	/**
	 * kill the player
	 */
	public void kill()
	{
		isAlive = false;
		isDying = false;
	}
	public boolean isAlive()
	{
		return isAlive;
	}
	public boolean isDying()
	{
		return isDying;
	}
	public void setIsDying(boolean isDying)
	{
		this.isDying = isDying;
	}
	/**
	 * change of health limit, if current health is greater than health limit after change,
	 * make it equal to health limit
	 * @param n
	 */
	public void changeHealthLimitTo(int n)
	{
		hero.changeHealthLimitTo(n);
		if(healthCurrent > hero.getHealthLimit())
			changeHealthCurrentTo(hero.getHealthLimit());
	}
	/**
	 * change of health limit, if current health is greater than health limit after change,
	 * make it equal to health limit
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
	 * @param n
	 */
	public void changeHealthCurrentTo(int n)
	{
		healthCurrent = n;
		hero.changeCardLimitTo(healthCurrent);
		if(healthCurrent < 1)//is dying
			isDying = true;
		else
			isDying = false;
	}
	/**
	 * strict change of health (i.e. no damage, does not invoke skills).
	 * @param n
	 */
	public void changeHealthCurrentBy(int n)
	{
		healthCurrent += n;
		hero.changeCardLimitTo(healthCurrent);
		if(healthCurrent < 1)//is dying
			isDying = true;
		else
			isDying = false;
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
		return temp;
	}

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
		int range = 1;//default reach distance
		if(weaponEquipped)
			range += (weapon.getRange()-1);//plus weapon range
		if(horseMinusEquipped)//plus horse range
			range++;
		return range;
	}
	/**
	 * Reach distance, excluding weapon range
	 * @return reach distance
	 */
	public int getReachDistance()
	{
		int distance = 1;//default reach distance
		if(horseMinusEquipped)
			distance++;//plus horse range
		return distance;
	}
	/**
	 * Distance to another player, given number of players alive
	 * @param player
	 * @param numberOfPlayersAlive
	 * @return a number that can be smaller than 1
	 */
	public int getDistanceTo(Player player,int numberOfPlayersAlive)
	{
		int counterclockwise = Math.abs(player.position - position);//counterclockwise distance
		int clockwise = numberOfPlayersAlive-Math.abs(position-player.position);//clockwise distance
		int shorter = counterclockwise >= clockwise ? clockwise : counterclockwise;//choose shorter
		if(isEquippedHorseMinus())
			shorter--;//minus horse range
		if(player.isEquippedHorsePlus())
			shorter++;//plus other player's horse range
		return shorter;
	}
	/**
	 * check whether player is in the attack range
	 * @param player
	 * @param numberOfPlayersAlive
	 * @return true on yes, false on no
	 */
	public boolean isPlayerInRange(Player player,int numberOfPlayersAlive)
	{
		return getDistanceTo(player,numberOfPlayersAlive) <= getAttackRange();
	}
	/**
	 * check whether player is in the reach distance
	 * @param player
	 * @param numberOfPlayersAlive
	 * @return true on yes, false on no
	 */
	public boolean isPlayerInDistance(Player player, int numberOfPlayersAlive)
	{
		return getDistanceTo(player, numberOfPlayersAlive) <= getReachDistance();
	}
	//************** methods related to damage *******************
	public abstract void takeDamage(Damage damage);

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
	/**
	 * Use this for most comparisons
	 * @param p
	 * @return 
	 */
	public boolean isEqualTo(PlayerInfo p)
	{
		return position == p.getPosition();
	}
	
}
