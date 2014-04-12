package player;

import heroes.Hero;

import java.util.ArrayList;
import java.util.List;

import listener.CardDisposalListener;
import listener.CardOnHandListener;
import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import cards.equipments.HorseMinus;
import cards.equipments.HorsePlus;
import cards.equipments.shields.Shield;
import cards.equipments.weapons.Weapon;
import core.PlayerInfo;

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
	public void addCards(List<Card> cards)
	{
		for(Card card : cards)
			addCard(card);
	}

	public abstract void useCard(Card card);
	/**
	 * <li>{@link CardOnHandListener} notified
	 * @param cards
	 */
	public void useCards(List<Card> cards)
	{
		for(Card card : cards)
			useCard(card);
	}

	public abstract void discardCard(Card card);
	/**
	 * <li>{@link CardDisposalListener} notified
	 * @param cards
	 */
	public void discardCards(List<Card> cards)
	{
		for(Card card : cards)
			discardCard(card);
	}
	/**
	 * Simply remove from hand, not discarded or used
	 * <li>{@link CardOnHandListener} notified
	 * @param card
	 */
	public abstract void removeCardFromHand(Card card);
	
	public abstract int getCardsOnHandCount();
	
	public List<Equipment> getEquipments()
	{
		List<Equipment> list = new ArrayList<Equipment>();
		if(weaponEquipped)
			list.add(weapon);
		if(shieldEquipped)
			list.add(shield);
		if(horsePlusEquipped)
			list.add(horsePlus);
		if(horseMinusEquipped)
			list.add(horseMinus);
		return list;
	}
	//**************** methods related to equipments ******************
	public boolean isEquipped()
	{
		return weaponEquipped || shieldEquipped || 
				horsePlusEquipped || horseMinusEquipped;
	}
	public boolean isEquippedWith(Equipment e)
	{
		return e.equals(weapon) || e.equals(shield) || e.equals(horseMinus) || e.equals(horsePlus);
	}
	public boolean isEquipped(EquipmentType type)
	{
		switch(type)
		{
			case WEAPON:
				return weaponEquipped;
			case SHIELD:
				return shieldEquipped;
			case HORSEPLUS:
				return horsePlusEquipped;
			case HORSEMINUS:
				return horseMinusEquipped;
			default:
				System.err.println("error: equipment type unidentified");
				return false;
		}
	}

	public Equipment getEquipment(EquipmentType type)
	{
		switch(type)
		{
			case WEAPON:
				return weapon;
			case SHIELD:
				return shield;
			case HORSEPLUS:
				return horsePlus;
			case HORSEMINUS:
				return horseMinus;
			default:
				System.err.println("error: equipment type unidentified");
				return null;
		}
	}

	public Shield getShield()
	{
		return shield;
	}
	
	public Weapon getWeapon()
	{
		return weapon;
	}
	public void unequip(EquipmentType type)
	{
		switch(type)
		{
			case WEAPON:
				weapon = null;
				weaponEquipped = false;
				break;
			case SHIELD:
				shield = null;
				shieldEquipped = false;
				break;
			case HORSEPLUS:
				horsePlus = null;
				horsePlusEquipped = false;
				break;
			case HORSEMINUS:
				horseMinus = null;
				horseMinusEquipped = false;
				break;
		}
	}

	public void equip(Equipment equipment)
	{
		switch(equipment.getEquipmentType())
		{
			case HORSEPLUS:
				unequip(EquipmentType.HORSEPLUS);
				horsePlus = (HorsePlus) equipment;
				horsePlusEquipped = true;
				break;
			case HORSEMINUS:
				unequip(EquipmentType.HORSEMINUS);
				horseMinus = (HorseMinus)equipment;
				horseMinusEquipped = true;
				break;
			case WEAPON:
				unequip(EquipmentType.WEAPON);
				weapon = (Weapon)equipment;
				weaponEquipped = true;
				break;
			case SHIELD:
				unequip(EquipmentType.SHIELD);
				shield = (Shield)equipment;
				shieldEquipped = true;
				break;
		}
	}

	//************** methods related to range/distance *************
	/**
	 * Attack range, not distance
	 * @return attack range
	 */
	public int getAttackRange()
	{
		if(weaponEquipped)
			return weapon.getRange();//plus weapon range
		return 1;
	}
	/**
	 * Distance to another player, given number of players alive
	 * @param player
	 * @param numberOfPlayersAlive
	 * @return a number that can be smaller than 1
	 */
	public int getDistanceTo(Player player,int numberOfPlayersAlive)
	{
		int smaller = Math.min(position, player.position);
		int larger = Math.max(position, player.position);
		int shorter = Math.min(larger - smaller, smaller + numberOfPlayersAlive - larger);
		if(isEquipped(EquipmentType.HORSEMINUS))
			shorter--;//minus horse range
		if(player.isEquipped(EquipmentType.HORSEPLUS))
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
		return getDistanceTo(player, numberOfPlayersAlive) <= 1;
	}
	//************** methods related to damage *******************
	public abstract void takeDamage(int amount);

	@Override
	public int hashCode()
	{
		return position;
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
	public boolean matches(PlayerInfo p)
	{
		return position == p.getPosition();
	}
	
}
