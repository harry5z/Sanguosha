package player;

import heroes.Blank;

import java.util.ArrayList;
import java.util.Stack;

import update.DisposalOfCards;
import update.DrawCardsFromDeck;
import update.NextStage;
import update.StageUpdate;
import listener.ClientListener;
import listener.GameListener;
import core.Card;
import core.Equipment;
import core.Operation;
import core.Player;
import core.PlayerInfo;
import core.Update;

public class PlayerOriginalClientComplete extends PlayerOriginalClientSimple implements ClientListener
{
	private ArrayList<Card> cardsOnHand;
	private transient GameListener gameListener;
	
	//private settings
	private transient StageUpdate currentStage;
	private transient ArrayList<PlayerOriginalClientSimple> otherPlayers;
		
		
	//in-game interactive properties
	private Card cardActivated;
	private Operation operation;
	//private ArrayList<Player> targetsSelected;
	//private int targetSelectionLimit;
	private ArrayList<Card> cardsUsedThisTurn;
	//private Stack<Update> updateStack;
	private Update updateToSend;
	public PlayerOriginalClientComplete(String name, int position) 
	{
		super(name, position);
		cardsOnHand = new ArrayList<Card>();
		//init global settings
		otherPlayers = new ArrayList<PlayerOriginalClientSimple>();
		
		//init in-game interactive properties
		//cardActivated = null;
		//targetsSelected = new ArrayList<Player>();
		//targetSelectionLimit = 1;
		//updateStack = new Stack<Update>();
		updateToSend = null;
		operation = null;
	}
	@Override
	public void onNotified(Update update)
	{
		update.playerOperation(this);
	}

	public void registerGameListener(GameListener listener)
	{
		gameListener = listener;
	}
	public ArrayList<Card> getCardsOnHand()
	{
		return cardsOnHand;
	}
	@Override
	public void addCard(Card card)
	{
		super.addCard(card);
		cardsOnHand.add(card);
	}
	@Override
	public void useCard(Card card)
	{
		super.useCard(card);
		cardsOnHand.remove(card);
	}
	@Override
	public void discardCard(Card card)
	{
		super.discardCard(card);
		cardsOnHand.remove(card);
	}
	
	@Override
	public Equipment equip(Equipment equipment)
	{
		Equipment e = super.equip(equipment);
		
//		if(e != null)
//			sendToMaster(new DisposalOfCards(this,e));
		return e;
	}
	public int getNumberOfPlayersAlive()
	{
		int alive = 1;//self
		for(Player p : otherPlayers)
			if(p.isAlive())
				alive++;
		return alive;
	}

	//************** methods related to properties ***************
	/**
	 * <li>{@link GameListener} notified
	 * @param player
	 */
	public void addOtherPlayer(PlayerInfo player)
	{
		PlayerOriginalClientSimple p = new PlayerOriginalClientSimple(player.getName(),player.getPosition());
		p.setHero(new Blank());
		otherPlayers.add(p);
		gameListener.onPlayerAdded(p);
	}
	public ArrayList<PlayerOriginalClientSimple> getOtherPlayers()
	{
		return otherPlayers;
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
	//**************** methods related to game flow ***************
	public void drawCards()
	{
		DrawCardsFromDeck update = new DrawCardsFromDeck(this,2);
		sendToMaster(update);
	}
	/**
	 * <li>{@link GameListener} notified
	 */
	@Override
	public void startDealing()
	{
		gameListener.onEndSetEnabled(true);
		for(Card card : cardsOnHand)
		{
			if(card.isActivatableBy(this))
				gameListener.onCardSetSelectable(card, true);
			else
				gameListener.onCardSetSelectable(card, false);
		}
	}
	/**
	 * <li>No card selected
	 * <li>No target selected
	 * <li>No player enabled(targetSelection off)
	 * <li>No update to send
	 * <li>confirm disabled
	 */
	@Override
	public void endDealing()
	{
		if(operation != null)
		{
			operation.onCancelledBy(this);
		}
		for(Card card : cardsOnHand)
			gameListener.onCardSetSelectable(card, false);
		gameListener.onConfirmSetEnabled(false);
		gameListener.onCancelSetEnabled(false);
		gameListener.onEndSetEnabled(false);
//		for(Card card : cardsSelected)
//			gameListener.onCardUnselected(card);
//		cardsSelected.clear();
//		for(Player target : targetsSelected)
//			gameListener.onTargetUnselected(target);
//		targetsSelected.clear();
		gameListener.onSendToMaster(new StageUpdate(getPlayerInfo(),StageUpdate.TURN_DISCARD_BEGINNING));
	}
	@Override
	public void endTurn()
	{
		super.endTurn();
		cardsUsedThisTurn.clear();
	}
	//**************** methods related to interactions ****************
	public Update getUpdateToSend()
	{
		return updateToSend;
	}
	public void setUpdateToSend(Update update)
	{
		updateToSend = update;
	}
	public void setOperation(Operation op)
	{
		operation = op;
	}
	public void setAllCardsOnHandSelectable(boolean selectable)
	{
		for(Card card : cardsOnHand)
			setCardSelectable(card,selectable);
	}
	/**
	 * select a card on hand, done by Gui
	 * <li>{@link GameListener} notified
	 * @param card
	 */
	public void chooseCardOnHand(Card card)
	{
		if(operation == null)//nothing activated 
		{
			card.onActivatedBy(this);
		}
		else//something activated
		{
			operation.onCardSelected(this, card);
		}
//		cardsSelected.add(card);
//		gameListener.onCardSelected(card);
//		if(cardsSelected.size() > cardSelectionLimit)
//			gameListener.onCardUnselected(cardsSelected.remove(0));
	}
	public void choosePlayer(PlayerOriginal player)
	{
		operation.onPlayerSelected(this, player);
	}
	/**
	 * unselect a card on hand, done by Gui
	 * <li>{@link GameListener} notified
	 * @param card
	 */
	public void setCardOnHandSelected(Card card, boolean isSelected)
	{
		if(isSelected)
			gameListener.onCardSelected(card);
		else
			gameListener.onCardUnselected(card);
//		cardsSelected.remove(card);
		
	}
//	public void setCardSelectionLimit(int limit)
//	{
//		cardSelectionLimit = limit;
//	}
//	public void setTargetSelectionLimit(int limit)
//	{
//		targetSelectionLimit = limit;
//	}
	public void setCardSelectableByName(String cardName,boolean selectable)
	{
		for(Card card : cardsOnHand)
			if(card.getName().equals(cardName))
				gameListener.onCardSetSelectable(card,selectable);
	}
	public void setCardSelectableByType(int cardType,boolean selectable)
	{
		for(Card card : cardsOnHand)
			if(card.getType() == cardType)
				gameListener.onCardSetSelectable(card,selectable);
	}
	public void setCardSelectable(Card card, boolean selectable)
	{
		gameListener.onCardSetSelectable(card, selectable);
	}
//	public boolean isSelected(Card card)
//	{
//		return cardsSelected.contains(card);
//	}
	public void setTargetSelectable(PlayerInfo player,boolean selectable)
	{
		gameListener.onTargetSetSelectable(player, selectable);
	}
	/**
	 * update of gui
	 * <li>{@link GameListener}
	 * @param player
	 */
	public void selectTarget(PlayerInfo player)
	{
	//	targetsSelected.add(player);
		gameListener.onTargetSelected(player);
	//	if(targetsSelected.size() > targetSelectionLimit)
	//		gameListener.onTargetUnselected(targetsSelected.remove(0));
	}

	public void unselectTarget(PlayerInfo player)
	{
//		targetsSelected.remove(player);
		gameListener.onTargetUnselected(player);
	}
//	public boolean isSelected(Player player)
//	{
//		return targetsSelected.contains(player);
//	}
	public void setConfirmEnabled(boolean isEnabled)
	{
		gameListener.onConfirmSetEnabled(isEnabled);
	}
	public void setCancelEnabled(boolean isEnabled)
	{
		gameListener.onCancelSetEnabled(isEnabled);
	}
	public void confirm()
	{
		operation.onConfirmedBy(this);
	}
	public void cancel()
	{
		operation.onCancelledBy(this);
	}

	public PlayerOriginal findMatch(PlayerInfo p)
	{
		for(PlayerOriginalClientSimple player : otherPlayers)
			if(player.isEqualTo(p))
				return player;
		if(this.isEqualTo(p))
			return this;
		return null;
	}
}