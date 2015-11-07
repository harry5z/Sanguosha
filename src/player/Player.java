package player;

import heroes.original.HeroOriginal;
import listeners.game.CardDisposalListener;
import listeners.game.CardOnHandListener;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private HeroOriginal hero;
	
	//********* in-game properties ***********
	private int healthCurrent;

	
	private boolean flipped;//whether player is flipped (not implemented yet)
	private boolean isAlive;//whether player is alive
	private boolean dying;//whether player is in the near-death stage
	
	//********* cards other than cardsOnHand, public to all other players *********
	private Weapon weapon;
	private Shield shield;
	private HorsePlus horsePlus;
	private HorseMinus horseMinus;
	
	private final PlayerInfo info;
	
	public Player(String name, int position)
	{
		this.name = name;
		this.position = position;
		this.info = new PlayerInfo(name, position);
		init();
	}
	private void init()
	{
		//init in-game properties
		isAlive = true;
		dying = false;
		
		//init other properties
		weapon = null;
		shield = null;
		horsePlus = null;
		horseMinus = null;
	}
	public final PlayerInfo getPlayerInfo()
	{
		return info;
	}
	/**
	 * upon setting a hero
	 * @param hero
	 */
	public void setHero(HeroOriginal hero)
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
	public HeroOriginal getHero()
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
		dying = false;
	}
	public boolean isAlive()
	{
		return isAlive;
	}
	public boolean isDying()
	{
		return dying;
	}
	public void setIsDying(boolean isDying)
	{
		this.dying = isDying;
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
			dying = true;
		else
			dying = false;
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
			dying = true;
		else
			dying = false;
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
	public void addCards(Collection<Card> cards)
	{
		for(Card card : cards)
			addCard(card);
	}

	public abstract void useCard(Card card);
	/**
	 * <li>{@link CardOnHandListener} notified
	 * @param cards
	 */
	public void useCards(Collection<Card> cards)
	{
		for(Card card : cards)
			useCard(card);
	}

	public abstract void discardCard(Card card);
	/**
	 * <li>{@link CardDisposalListener} notified
	 * @param cards
	 */
	public void discardCards(Collection<Card> cards)
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
	
	/**
	 * Check the number of cards on hand
	 * @return number of cards on hand
	 */
	public abstract int getHandCount();

	//**************** methods related to equipments ******************
	/**
	 * get all currently equipped equipments
	 * @return a set of equipments
	 */
	public Set<Equipment> getEquipments()
	{
		Set<Equipment> set = new HashSet<Equipment>();
		if(weapon != null)
			set.add(weapon);
		if(shield != null)
			set.add(shield);
		if(horsePlus != null)
			set.add(horsePlus);
		if(horseMinus != null)
			set.add(horseMinus);
		return set;
	}
	/**
	 * Check whether player is equipped with anything
	 * @return true if any of weapon/shield/horse is equipped
	 */
	public boolean isEquipped()
	{
		return weapon != null || shield != null || 
				horsePlus != null || horseMinus != null;
	}
	/**
	 * Check whether player is equipped with a specific equipment
	 * @param e : the equipment
	 * @return true if player is equipped with e, false otherwise
	 */
	public boolean isEquippedWith(Equipment e)
	{
		return e.equals(weapon) || e.equals(shield) || e.equals(horseMinus) || e.equals(horsePlus);
	}
	/**
	 * Check whether player is equipped with a specific type of equipment
	 * @param type : the equipment type
	 * @return true if player is equipped such type of equipment, false otherwise
	 */
	public boolean isEquipped(EquipmentType type)
	{
		return getEquipment(type) != null;
	}
	/**
	 * Obtain equipment by type<br>
	 * It is suggested to check whether player is equipped first
	 * @param type : equipment type
	 * @return equipment of the type, or null if not equipped
	 */
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
				break;
			case SHIELD:
				shield = null;
				break;
			case HORSEPLUS:
				horsePlus = null;
				break;
			case HORSEMINUS:
				horseMinus = null;
				break;
		}
	}

	/**
	 * equip a new equipment, overriding the old one
	 * if exists
	 * @param equipment : new equipment
	 */
	public void equip(Equipment equipment)
	{
		switch(equipment.getEquipmentType())
		{
			case HORSEPLUS:
				unequip(EquipmentType.HORSEPLUS);
				horsePlus = (HorsePlus) equipment;
				break;
			case HORSEMINUS:
				unequip(EquipmentType.HORSEMINUS);
				horseMinus = (HorseMinus)equipment;
				break;
			case WEAPON:
				unequip(EquipmentType.WEAPON);
				weapon = (Weapon)equipment;
				break;
			case SHIELD:
				unequip(EquipmentType.SHIELD);
				shield = (Shield)equipment;
				break;
		}
	}

	//************** methods related to range/distance *************
	/**
	 * Attack range, not distance
	 * <li> 1 by default
	 * @return attack range
	 */
	public int getAttackRange()
	{
		if(weapon != null)
			return weapon.getRange();//plus weapon range
		return 1; // by difault 1
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
		if(horseMinus != null)
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

	@Override
	public int hashCode()
	{
		return position;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof PlayerInfo)
			return position == (((PlayerInfo)obj).getPosition());
		else if(obj instanceof Player)
			return position == ((Player)obj).position;
		else
			return false;
	}
}
