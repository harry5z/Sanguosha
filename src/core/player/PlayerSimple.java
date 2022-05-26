package core.player;

import java.util.Collection;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;
import listeners.game.CardDisposalListener;
import listeners.game.CardOnHandListener;
import listeners.game.DelayedListener;
import listeners.game.EquipmentListener;
import listeners.game.HealthListener;
import listeners.game.HeroListener;
import utils.DelayedStackItem;
import utils.DelayedType;

/**
 * client side simple player implementation, used to hold information of
 * "other players"
 * 
 * @author Harry
 *
 */
public class PlayerSimple extends Player {
	
	private HealthListener healthListener;
	private CardOnHandListener cardsOnHandListener;
	private EquipmentListener equipmentListener;
	private CardDisposalListener disposalListener;
	private HeroListener heroListener;
	private DelayedListener delayedListener;
	
	private int cardsCount;
	private boolean wineEffective; // whether wine is currently effective

	public PlayerSimple(String name, int position) {
		super(name, position);
		cardsCount = 0;
		wineEffective = false;
	}

	/**
	 * register health listener to monitor the change in health
	 * 
	 * @param listener
	 */
	public void registerHealthListener(HealthListener listener) {
		healthListener = listener;
	}

	/**
	 * register card-on-hand listener to monitor the change of card-on-hand
	 * 
	 * @param listener
	 */
	public void registerCardOnHandListener(CardOnHandListener listener) {
		cardsOnHandListener = listener;
	}

	/**
	 * register equipment listener to monitor the change of equipments
	 * 
	 * @param listener
	 */
	public void registerEquipmentListener(EquipmentListener listener) {
		equipmentListener = listener;
	}

	/**
	 * register card disposal listener to monitor the use and disposal of cards
	 * 
	 * @param listener
	 */
	public void registerCardDisposalListener(CardDisposalListener listener) {
		disposalListener = listener;
	}
	
	public void registerHeroListener(HeroListener listener) {
		this.heroListener = listener;
	}
	
	public void registerDelayedListener(DelayedListener listener) {
		this.delayedListener = listener;
	}

	/**
	 * <li>{@link HealthListener} notified
	 */
	@Override
	public void setHealthLimit(int n) {
		super.setHealthLimit(n);
		healthListener.onSetHealthLimit(n);
	}

	/**
	 * <li>{@link HealthListener} notified
	 */
	@Override
	public void changeHealthLimitBy(int n) {
		super.changeHealthLimitBy(n);
		healthListener.onSetHealthLimit(getHealthLimit());
	}

	/**
	 * <li>{@link HealthListener} notified
	 */
	@Override
	public void setHealthCurrent(int n) {
		super.setHealthCurrent(n);
		healthListener.onSetHealthCurrent(n);
	}

	/**
	 * <li>{@link HealthListener} notified
	 * 
	 * @param n
	 */
	@Override
	public void changeHealthCurrentBy(int n) {
		super.changeHealthCurrentBy(n);
		healthListener.onHealthChangedBy(n);
	}

	/**
	 * <li>{@link CardOnHandListener} notified
	 * 
	 * @param card
	 */
	@Override
	public void addCard(Card card) {
		cardsCount++;
		cardsOnHandListener.onCardAdded(card);
	}

	/**
	 * <li>{@link CardOnHandListener} notified
	 * <li>{@link CardDisposalListener} notified
	 * 
	 * @param card
	 * @throws InvalidPlayerCommandException 
	 */
	@Override
	public void useCard(Card card) throws InvalidPlayerCommandException {
		cardsCount--;
		cardsOnHandListener.onCardRemoved(card);
		disposalListener.onCardUsed(card);
	}
	
	@Override
	public void setHero(Hero hero) {
		super.setHero(hero);
		heroListener.onHeroRegistered(hero);
	}

	/**
	 * <li>{@link CardDisposalListener} notified</li>
	 * <li>{@link CardOnHandListener} notified</li>
	 * @param card
	 */
	@Override
	public void discardCard(Card card) throws InvalidPlayerCommandException {
		cardsCount--;
		cardsOnHandListener.onCardRemoved(card);
		disposalListener.onCardDisposed(card);
	}
	
	@Override
	public int getHandCount() {
		return cardsCount;
	}

	public CardDisposalListener getDisposalListener() {
		return disposalListener;
	}

	/**
	 * Show a card on the disposal area. The card is neither used or discarded.
	 * <li>{@link CardDisposalListener} notified
	 * 
	 * @param card
	 */
	public void showCard(Card card) {
		disposalListener.onCardShown(card);
	}

	/**
	 * Show a collection of cards
	 * 
	 * @param cards
	 */
	public void showCards(Collection<? extends Card> cards) {
		for (Card card : cards)
			showCard(card);
	}

	@Override
	public void removeCardFromHand(Card card) throws InvalidPlayerCommandException {
		cardsCount--;
		cardsOnHandListener.onCardRemoved(card);
	}
	
	@Override
	public void pushDelayed(Card card, DelayedType type) {
		super.pushDelayed(card, type);
		this.delayedListener.onDelayedAdded(card, type);
	}
	
	@Override
	public DelayedStackItem removeDelayed(DelayedType type) {
		DelayedStackItem item = super.removeDelayed(type);
		this.delayedListener.onDelayedRemove(item.type);
		return item;
	}
	
	@Override
	public DelayedStackItem removeDelayed(Card card) {
		DelayedStackItem item = super.removeDelayed(card);
		this.delayedListener.onDelayedRemove(item.type);
		return item;
	}

	/**
	 * discard an equipment
	 * <li>{@link EquipmentListener} notified
	 * 
	 * @param type
	 * @throws InvalidPlayerCommandException 
	 */
	@Override
	public void unequip(EquipmentType type) throws InvalidPlayerCommandException {
		super.unequip(type);
		equipmentListener.onUnequipped(type);
	}

	/**
	 * equip new equipment
	 * <li>{@link EquipmentListener} notified
	 * 
	 * @param equipment
	 *            : new equipment
	 * @throws InvalidPlayerCommandException 
	 */
	@Override
	public void equip(Equipment equipment) throws InvalidPlayerCommandException {
		super.equip(equipment);
		equipmentListener.onEquipped(equipment);
	}

	/**
	 * {@link HealthListener} notified
	 */
	@Override
	public void kill() {
		super.kill();
		healthListener.onDeath();
		// Player role is revealed upon death
		heroListener.onRoleRevealed(getRole());
	}

	public void clearDisposalArea() {
		disposalListener.refresh();
	}
	
	public void useWine() throws InvalidPlayerCommandException {
		wineEffective = true;
		heroListener.onWineEffective(true);
	}
	
	public boolean isWineEffective() {
		return this.wineEffective;
	}
	
	public void resetWineEffective() {
		if (this.wineEffective) {
			this.wineEffective = false;
			heroListener.onWineEffective(false);
		}
	}
	
	@Override
	public void setRole(Role role) {
		super.setRole(role);
		heroListener.onRoleAssigned(role);
	}
	
	@Override
	public void flip() {
		super.flip();
		heroListener.onFlipped(isFlipped());
	}
	
	@Override
	public void chain() {
		super.chain();
		heroListener.onChained(isChained());
	}
	
	@Override
	public void setChained(boolean chained) {
		super.setChained(chained);
		heroListener.onChained(chained);
	}
}
