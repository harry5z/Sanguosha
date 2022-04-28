package core.player;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import cards.equipments.HorseMinus;
import cards.equipments.HorsePlus;
import cards.equipments.shields.Shield;
import cards.equipments.weapons.Weapon;
import core.heroes.Hero;
import exceptions.server.game.GameStateErrorException;
import exceptions.server.game.InvalidPlayerCommandException;
import listeners.game.CardDisposalListener;
import listeners.game.CardOnHandListener;
import utils.DelayedStackItem;
import utils.DelayedType;

/**
 * The player class, defines common traits of different implementations of
 * players
 * 
 * @author Harry
 *
 */
public abstract class Player {
	// ********* personal properties *********
	private String name;
	private int position;
	private Hero hero;

	// ********* in-game properties ***********
	private int healthCurrent;

	private boolean flipped;// whether player is flipped (not implemented yet)
	private boolean chained; // whether player is chained (by Chain)
	private boolean isAlive;// whether player is alive
	private boolean dying;// whether player is in the near-death stage

	// ********* cards other than cardsOnHand, public to all other players
	// *********
	private Weapon weapon;
	private Shield shield;
	private HorsePlus horsePlus;
	private HorseMinus horseMinus;
	
	// Delayed Special
	private Stack<DelayedStackItem> delayedStack;

	private final PlayerInfo info;

	public Player(String name, int position) {
		this.name = name;
		this.position = position;
		this.info = new PlayerInfo(name, position);
		init();
	}

	private void init() {
		// init in-game properties
		isAlive = true;
		dying = false;
		chained = false;

		// init other properties
		weapon = null;
		shield = null;
		horsePlus = null;
		horseMinus = null;
		
		this.delayedStack = new Stack<>();
	}

	public final PlayerInfo getPlayerInfo() {
		return info;
	}

	/**
	 * upon setting a hero
	 * 
	 * @param hero
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
		healthCurrent = hero.getHealthLimit();
	}

	/**
	 * use this very carefully, usually only at game start
	 * 
	 * @param position
	 */
	public final void setPosition(int position) {
		this.position = position;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public Hero getHero() {
		return hero;
	}

	public final int getPosition() {
		return position;
	}

	public final String getName() {
		return name;
	}

	// ************* methods related to flipping ***************
	public final boolean isFlipped() {
		return flipped;
	}

	public void flip() {
		flipped = !flipped;
	}
	
	public final boolean isChained() {
		return this.chained;
	}
	
	public void chain() {
		this.chained = !this.chained;
	}
	
	public void setChained(boolean chained) {
		this.chained = chained;
	}

	// **************** methods related to health *******************
	/**
	 * kill the player
	 */
	public void kill() {
		isAlive = false;
		dying = false;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean isDying() {
		return dying;
	}

	public void setIsDying(boolean isDying) {
		this.dying = isDying;
	}

	/**
	 * change of health limit, if current health is greater than health limit
	 * after change, make it equal to health limit
	 * 
	 * @param n
	 */
	public void changeHealthLimitTo(int n) {
		hero.changeHealthLimitTo(n);
		if (healthCurrent > hero.getHealthLimit())
			changeHealthCurrentTo(hero.getHealthLimit());
	}

	/**
	 * change of health limit, if current health is greater than health limit
	 * after change, make it equal to health limit
	 * 
	 * @param n
	 */
	public void changeHealthLimitBy(int n) {
		hero.changeHealthLimitBy(n);
		if (healthCurrent > hero.getHealthLimit())
			changeHealthCurrentTo(hero.getHealthLimit());

	}

	public int getHealthLimit() {
		return hero.getHealthLimit();
	}

	/**
	 * strict change of health (i.e. no damage, does not invoke skills).
	 * 
	 * @param n
	 */
	public void changeHealthCurrentTo(int n) {
		healthCurrent = n;
		hero.changeCardLimitTo(healthCurrent);
		if (healthCurrent < 1)// is dying
			dying = true;
		else
			dying = false;
	}

	/**
	 * strict change of health (i.e. no damage, does not invoke skills).
	 * 
	 * @param n
	 */
	public void changeHealthCurrentBy(int n) {
		healthCurrent += n;
		hero.changeCardLimitTo(healthCurrent);
		if (healthCurrent < 1)// is dying
			dying = true;
		else
			dying = false;
	}

	public int getHealthCurrent() {
		return healthCurrent;
	}
	
	public boolean isDamaged() {
		return this.getHealthCurrent() < this.getHealthLimit();
	}

	// **************** methods related to cards on hand ***************
	public int getCardOnHandLimit() {
		return hero.getCardOnHandLimit();
	}

	public abstract void addCard(Card card);

	/**
	 * <li>{@link CardOnHandListener} notified
	 * 
	 * @param cards
	 */
	public void addCards(Collection<Card> cards) {
		for (Card card : cards)
			addCard(card);
	}

	public abstract void useCard(Card card) throws InvalidPlayerCommandException;

	/**
	 * <li>{@link CardOnHandListener} notified</li>
	 * <li>{@link CardDisposalListener} notified</li>
	 * 
	 * @param cards
	 */
	public void useCards(Collection<Card> cards) throws InvalidPlayerCommandException {
		for (Card card : cards)
			useCard(card);
	}

	public abstract void discardCard(Card card) throws InvalidPlayerCommandException;

	/**
	 * <li>{@link CardDisposalListener} notified</li>
	 * <li>{@link CardOnHandListener} notified</li>
	 * 
	 * @param cards
	 */
	public void discardCards(Collection<Card> cards) throws InvalidPlayerCommandException {
		for (Card card : cards)
			discardCard(card);
	}

	/**
	 * Simply remove from hand, not discarded or used
	 * <li>{@link CardOnHandListener} notified
	 * 
	 * @param card
	 */
	public abstract void removeCardFromHand(Card card) throws InvalidPlayerCommandException;

	/**
	 * Check the number of cards on hand
	 * 
	 * @return number of cards on hand
	 */
	public abstract int getHandCount();

	// **************** methods related to equipments ******************
	/**
	 * get all currently equipped equipments
	 * 
	 * @return a set of equipments
	 */
	public Set<Equipment> getEquipments() {
		Set<Equipment> set = new HashSet<Equipment>();
		if (weapon != null)
			set.add(weapon);
		if (shield != null)
			set.add(shield);
		if (horsePlus != null)
			set.add(horsePlus);
		if (horseMinus != null)
			set.add(horseMinus);
		return set;
	}

	/**
	 * Check whether player is equipped with anything
	 * 
	 * @return true if any of weapon/shield/horse is equipped
	 */
	public boolean isEquipped() {
		return weapon != null || shield != null || horsePlus != null || horseMinus != null;
	}

	/**
	 * Check whether player is equipped with a specific equipment
	 * 
	 * @param e
	 *            : the equipment
	 * @return true if player is equipped with e, false otherwise
	 */
	public boolean isEquippedWith(Equipment e) {
		return e.equals(weapon) || e.equals(shield) || e.equals(horseMinus) || e.equals(horsePlus);
	}

	/**
	 * Check whether player is equipped with a specific type of equipment
	 * 
	 * @param type
	 *            : the equipment type
	 * @return true if player is equipped such type of equipment, false
	 *         otherwise
	 */
	public boolean isEquipped(EquipmentType type) {
		return getEquipment(type) != null;
	}

	/**
	 * Obtain equipment by type<br>
	 * It is suggested to check whether player is equipped first
	 * 
	 * @param type
	 *            : equipment type
	 * @return equipment of the type, or null if not equipped
	 */
	public Equipment getEquipment(EquipmentType type) {
		switch (type) {
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

	public Shield getShield() {
		return shield;
	}

	public Weapon getWeapon() {
		return weapon;
	}
	
	public void pushDelayed(Card card, DelayedType type) {
		DelayedStackItem item = new DelayedStackItem(card, type);
		if (this.delayedStack.contains(item)) {
			throw new GameStateErrorException("Delayed type " + type.toString() + " already exists");
		}
		this.delayedStack.push(item);
	}
	
	public Queue<DelayedStackItem> getDelayedQueue() {
		Queue<DelayedStackItem> queue = new LinkedList<>();
		// Stack's iterator is FIFO order - a bug in Java implementation
		for (int i = this.delayedStack.size() - 1; i >= 0; i--) {
			queue.add(this.delayedStack.get(i));
		}
		return queue;
	}
	
	public boolean hasDelayedType(DelayedType type) {
		for (DelayedStackItem item : this.delayedStack) {
			if (item.type == type) {
				return true;
			}
		}
		return false;
	}
	
	public DelayedStackItem removeDelayed(DelayedType type) {
		DelayedStackItem ret = null;
		for (DelayedStackItem item : this.delayedStack) {
			if (item.type == type) {
				ret = item;
				break;
			}
		}
		this.delayedStack.remove(ret);
		return ret;
	}
	
	public DelayedStackItem removeDelayed(Card card) {
		DelayedStackItem ret = null;
		for (DelayedStackItem item : this.delayedStack) {
			if (item.delayed.equals(card)) {
				ret = item;
				break;
			}
		}
		this.delayedStack.remove(ret);
		return ret;
	}

	public void unequip(EquipmentType type) throws InvalidPlayerCommandException {
		switch (type) {
			case WEAPON:
				if (weapon == null) {
					throw new InvalidPlayerCommandException("weapon is not equipped");
				}
				weapon = null;
				break;
			case SHIELD:
				if (shield == null) {
					throw new InvalidPlayerCommandException("shield is not equipped");
				}
				shield = null;
				break;
			case HORSEPLUS:
				if (horsePlus == null) {
					throw new InvalidPlayerCommandException("horsePlus is not equipped");
				}
				horsePlus = null;
				break;
			case HORSEMINUS:
				if (horseMinus == null) {
					throw new InvalidPlayerCommandException("horseMinus is not equipped");
				}
				horseMinus = null;
				break;
		}
	}

	/**
	 * equip a new equipment, overriding the old one if exists
	 * 
	 * @param equipment
	 *            : new equipment
	 */
	public void equip(Equipment equipment) throws InvalidPlayerCommandException {
		switch (equipment.getEquipmentType()) {
			case HORSEPLUS:
				horsePlus = (HorsePlus) equipment;
				break;
			case HORSEMINUS:
				horseMinus = (HorseMinus) equipment;
				break;
			case WEAPON:
				weapon = (Weapon) equipment;
				break;
			case SHIELD:
				shield = (Shield) equipment;
				break;
		}
	}

	// ************** methods related to range/distance *************
	/**
	 * Attack range, not distance
	 * <li>1 by default
	 * 
	 * @return attack range
	 */
	public int getAttackRange() {
		if (weapon != null)
			return weapon.getRange();// plus weapon range
		return 1; // by default 1
	}

	/**
	 * Distance to another player, given number of players alive
	 * 
	 * @param player
	 * @param numberOfPlayersAlive
	 * @return a number that can be smaller than 1
	 */
	public int getDistanceTo(Player player, int numberOfPlayersAlive) {
		int smaller = Math.min(position, player.position);
		int larger = Math.max(position, player.position);
		int shorter = Math.min(larger - smaller, smaller + numberOfPlayersAlive - larger);
		if (horseMinus != null)
			shorter--;// minus horse range
		if (player.isEquipped(EquipmentType.HORSEPLUS))
			shorter++;// plus other player's horse range
		return shorter;
	}

	/**
	 * check whether player is in the attack range
	 * 
	 * @param player
	 * @param numberOfPlayersAlive
	 * @return true on yes, false on no
	 */
	public boolean isPlayerInAttackRange(Player player, int numberOfPlayersAlive) {
		return getDistanceTo(player, numberOfPlayersAlive) <= getAttackRange();
	}

	/**
	 * check whether player is in the reach distance
	 * 
	 * @param player
	 * @param numberOfPlayersAlive
	 * @return true on yes, false on no
	 */
	public boolean isPlayerInDistance(Player player, int numberOfPlayersAlive) {
		return getDistanceTo(player, numberOfPlayersAlive) <= 1;
	}

	@Override
	public int hashCode() {
		return position;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Player) ? position == ((Player) obj).position : false;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
