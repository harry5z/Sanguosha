package player;

import core.Card;
import core.Equipment;
import listener.*;

public class PlayerOriginalClientSimple extends PlayerOriginalMasterSimple
{
	private transient HealthListener healthListener;
	private transient CardOnHandListener cardsOnHandListener;
	private transient EquipmentListener equipmentListener;
	private transient CardDisposalListener disposalListener;
	
	public PlayerOriginalClientSimple(String name) 
	{
		super(name);
	}
	public PlayerOriginalClientSimple(String name, int position) 
	{
		super(name,position);
	}
	/**
	 * register health listener to monitor the change in health
	 * @param listener
	 */
	public void registerHealthListener(HealthListener listener)
	{
		healthListener = listener;
		healthListener.onSetHealthLimit(getHero().getHealthLimit());
		healthListener.onSetHealthCurrent(getHealthCurrent());
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
	public void registerCardDisposalListener(CardDisposalListener listener)
	{
		disposalListener = listener;
	}
	@Override
	public void changeHealthLimitTo(int n)
	{
		super.changeHealthLimitTo(n);
		healthListener.onSetHealthLimit(getHero().getHealthLimit());
	}
	@Override
	public void changeHealthLimitBy(int n)
	{
		super.changeHealthLimitBy(n);
		healthListener.onSetHealthLimit(getHero().getHealthLimit());
	}
	@Override
	public void changeHealthCurrentTo(int n)
	{
		super.changeHealthCurrentTo(n);
		healthListener.onSetHealthCurrent(n);
	}
	/**
	 * damage
	 * <li>{@link HealthListener} notified
	 * @param n
	 */
	@Override
	public void changeHealthCurrentBy(int n)
	{
		super.changeHealthCurrentBy(n);
		healthListener.onHealthChangedBy(n);
	}
	@Override
	public void addCard(Card card)
	{
		super.addCard(card);
		cardsOnHandListener.onCardAdded(card);
	}
	@Override
	public void useCard(Card card)
	{
		super.useCard(card);
		cardsOnHandListener.onCardRemoved(card);
		disposalListener.onCardUsed(card);
	}
	@Override
	public void discardCard(Card card)
	{
		super.discardCard(card);
		cardsOnHandListener.onCardRemoved(card);
		disposalListener.onCardDisposed(card);
	}
	@Override
	public Equipment unequip(int type)
	{
		Equipment e = super.unequip(type);
		equipmentListener.onUnequipped(type);
		disposalListener.onCardDisposed(e);
		return e;
	}
	@Override
	public Equipment equip(Equipment equipment)
	{
		Equipment e = super.equip(equipment);
		equipmentListener.onEquipped(equipment);
		return e;
	}
	public void clearDisposalArea()
	{
		disposalListener.refresh();
	}
}
