package core.player;

import java.util.Collection;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.heroes.original.HeroOriginal;
import exceptions.server.game.InvalidPlayerCommandException;
import listeners.game.CardDisposalListener;
import listeners.game.CardOnHandListener;
import listeners.game.EquipmentListener;
import listeners.game.HealthListener;

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
	private int cardsCount;

	public PlayerSimple(String name, int position) {
		super(name, position);
		cardsCount = 0;
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

	/**
	 * <li>{@link HealthListener} notified
	 */
	@Override
	public void changeHealthLimitTo(int n) {
		super.changeHealthLimitTo(n);
		healthListener.onSetHealthLimit(getHero().getHealthLimit());
	}

	/**
	 * <li>{@link HealthListener} notified
	 */
	@Override
	public void changeHealthLimitBy(int n) {
		super.changeHealthLimitBy(n);
		healthListener.onSetHealthLimit(getHero().getHealthLimit());
	}

	/**
	 * <li>{@link HealthListener} notified
	 */
	@Override
	public void changeHealthCurrentTo(int n) {
		super.changeHealthCurrentTo(n);
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
		cardOnHandListenerAction(cardsOnHandListener, card);
		disposalListener.onCardUsed(card);
	}
	
	protected void cardOnHandListenerAction(CardOnHandListener listener, Card card) {
		listener.onCardRemoved(card);
	}
	
	@Override
	public void setHero(HeroOriginal hero) {
		super.setHero(hero);
		healthListener.onSetHealthLimit(hero.getHealthLimit());
		healthListener.onSetHealthCurrent(hero.getHealthLimit());
	}

	/**
	 * <li>{@link CardDisposalListener} notified</li>
	 * <li>{@link CardOnHandListener} notified</li>
	 * @param card
	 */
	@Override
	public void discardCard(Card card) throws InvalidPlayerCommandException {
		cardsCount--;
		cardOnHandListenerAction(cardsOnHandListener, card);
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
		disposalListener.onCardDisposed(card);
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

	/**
	 * discard an equipment
	 * <li>{@link EquipmentListener} notified
	 * 
	 * @param type
	 * @throws InvalidPlayerCommandException 
	 */
	@Override
	public void unequip(EquipmentType type) throws InvalidPlayerCommandException {
		cardDisposalListenerAction(disposalListener, getEquipment(type));
		equipmentListener.onUnequipped(type);
		super.unequip(type);
	}
	
	protected void cardDisposalListenerAction(CardDisposalListener listener, Card card) {
		listener.onCardDisposed(card);
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
		equipmentListener.onUnequipped(EquipmentType.WEAPON);
		equipmentListener.onUnequipped(EquipmentType.SHIELD);
		equipmentListener.onUnequipped(EquipmentType.HORSEPLUS);
		equipmentListener.onUnequipped(EquipmentType.HORSEMINUS);
	}

	public void clearDisposalArea() {
		disposalListener.refresh();
	}

}
