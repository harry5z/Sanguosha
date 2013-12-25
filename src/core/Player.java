package core;

import java.io.Serializable;
import java.util.ArrayList;

import update.Damage;
import update.DrawCardsFromDeck;
import update.StageUpdate;
import update.Update;
import listener.*;
import equipments.HorseMinus;
import equipments.HorsePlus;
import equipments.Shield;
import equipments.Weapon;

public class Player implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1318272306004077876L;
	//personal properties
	private String name;
	private int position;
	private Hero hero;
	
	//in-game properties
	private int healthCurrent;
	private int attackLimit;
	private ArrayList<Card> cardsOnHand;
	private boolean flipped;
	private boolean isAlive;
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
	
	//private settings
	private transient StageUpdate currentStage;
	private transient ArrayList<Player> otherPlayers;
	private transient HealthListener healthListener;
	private transient CardOnHandListener cardsOnHandListener;
	private transient EquipmentListener equipmentListener;
	private transient GameListener gameListener;
	private transient CardDisposalListener disposalListener;

	//in-game interactive properties
	private ArrayList<Card> cardsSelected;
	private int cardSelectionLimit;
	
	public Player(String name,int position)
	{
		
		//init personal properties
		this.name = name;
		this.position = position;
		//init in-game properties
		cardsOnHand = new ArrayList<Card>();
		isAlive = true;
		weaponEquipped = false;
		shieldEquipped = false;
		horsePlusEquipped = false;
		horseMinusEquipped = false;
		attackLimit = 1;
		
		//init other properties
		decisionArea = new ArrayList<Card>();
		weapon = null;
		shield = null;
		horsePlus = null;
		horseMinus = null;
		
		//init global settings
		otherPlayers = new ArrayList<Player>();
		
		//init in-game interactive properties
		cardsSelected = new ArrayList<Card>();
		cardSelectionLimit = 1;
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
	/**
	 * register health listener to monitor the change in health
	 * @param listener
	 */
	public void registerHealthListener(HealthListener listener)
	{
		healthListener = listener;
		healthListener.onSetHealthLimit(hero.getHealthLimit());
		healthListener.onSetHealthCurrent(healthCurrent);
	}
	/**
	 * register card-on-hand listener to monitor the change of card-on-hand
	 * @param listener
	 */
	public void registerCardOnHandListener(CardOnHandListener listener)
	{
		cardsOnHandListener = listener;
	}
	/**
	 * register equipment listener to monitor the change of equipments
	 * @param listener
	 */
	public void registerEquipmentListener(EquipmentListener listener)
	{
		equipmentListener = listener;
	}
	public void registerGameListener(GameListener listener)
	{
		gameListener = listener;
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
	 * change of health limit
	 * <li>{@link HealthListener} notified
	 * @param n
	 */
	public void changeHealthLimitTo(int n)
	{
		hero.changeHealthLimitTo(n);
		if(healthCurrent > hero.getHealthLimit())
			changeHealthCurrentTo(hero.getHealthLimit());
		healthListener.onSetHealthLimit(hero.getHealthLimit());
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
		healthListener.onSetHealthLimit(hero.getHealthLimit());
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
		healthListener.onSetHealthCurrent(healthCurrent);
	}
	/**
	 * strict change of health (i.e. no damage, does not invoke skills).
	 * <li>{@link HealthListener} notified
	 * @param n
	 */
	public void changeHealthCurrentBy(int n)
	{
		healthCurrent += n;
		hero.changeCardLimitTo(healthCurrent);
		healthListener.onHealthChangedBy(n);
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
	public ArrayList<Card> getCardsOnHand()
	{
		return cardsOnHand;
	}
	/**
	 * <li>{@link CardOnHandListener} notified
	 * @param card
	 */
	public void addCard(Card card)
	{
		cardsOnHand.add(card);
		cardsOnHandListener.onCardAdded(card);
	}
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
	 * @param card
	 */
	public void useCard(Card card)
	{
		cardsOnHand.remove(card);
		cardsOnHandListener.onCardRemoved(card);
	}
	/**
	 * (CardOnHandListener notified)
	 * @param cards
	 */
	public void useCards(ArrayList<Card> cards)
	{
		for(Card card : cards)
			useCard(card);
	}
	public void discardCard(Card card)
	{
		
	}
	public int getCardsOnHandCount()
	{
		return cardsOnHand.size();
	}
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
	/**
	 * discard an equipment
	 * <li>{@link EquipmentListener} notified
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
		equipmentListener.onUnequipped(type);
		return temp;
	}
	/**
	 * equip new equipment, return the old one. Return null if nothing is replaced
	 * <li>{@link EquipmentListener} notified
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
		equipmentListener.onEquipped(equipment);
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
	public int getDistanceTo(Player player)
	{
		int counterclockwise = player.position - position;
		int clockwise = position+(otherPlayers.size()+1)-player.position;
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
	public boolean isPlayerInRange(Player player)
	{
		return getDistanceTo(player) <= getAttackRange();
	}
	/**
	 * check whether player is in the reach distance
	 * @param player
	 * @return true on yes, false on no
	 */
	public boolean isPlayerInDistance(Player player)
	{
		return getDistanceTo(player) <= getReachDistance();
	}
	//************** methods related to damage *******************
	public void takeDamage(Damage damage)
	{
		int amount = damage.getAmount();
		changeHealthCurrentBy(-amount);
	}
	//************** methods related to properties ***************
	/**
	 * <li>{@link GameListener} notified
	 * @param player
	 */
	public void addOtherPlayer(Player player)
	{
		otherPlayers.add(player);
		gameListener.onPlayerAdded(player);
	}
	public ArrayList<Player> getOtherPlayers()
	{
		return otherPlayers;
	}
	public Player findMatch(Player player)
	{
		for(Player p : otherPlayers)
			if(p.equals(player))
				return p;
		return null;
	}
	/**
	 * <li>{@link GameListener} notified
	 * @param update
	 */
	public void sendToMaster(Update update)
	{
		gameListener.onSendToMaster(update);
	}
	public void setCurrentStage(StageUpdate update)
	{
		currentStage = update;
	}
	public StageUpdate getCurrentStage()
	{
		return currentStage;
	}
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
	//**************** methods related to game flow ***************
	public void drawCards()
	{
		DrawCardsFromDeck update = new DrawCardsFromDeck(this,2);
		sendToMaster(update);
	}
	/**
	 * <li>{@link GameListener} notified
	 */
	public void startDealing()
	{
		gameListener.onTurnDealStarted();
	}
	
	//**************** methods related to interactions ****************
	/**
	 * select a card on hand, done by Gui
	 * <li>{@link GameListener} notified
	 * @param card
	 */
	public void selectCardOnHand(Card card)
	{
		cardsSelected.add(card);
		gameListener.onCardSelected(card);
		if(cardsSelected.size() > cardSelectionLimit)
			gameListener.onCardUnselected(cardsSelected.remove(0));
	}
	/**
	 * unselect a card on hand, done by Gui
	 * <li>{@link GameListener} notified
	 * @param card
	 */
	public void unselectCardOnHand(Card card)
	{
		cardsSelected.remove(card);
		gameListener.onCardUnselected(card);
	}
	public void setCardSelectionLimit(int limit)
	{
		cardSelectionLimit = limit;
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
}
