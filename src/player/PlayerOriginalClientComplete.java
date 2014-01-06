package player;

import heroes.Blank;

import java.util.ArrayList;

import net.Client;
import update.Damage;
import update.DrawCardsFromDeck;
import update.StageUpdate;
import listener.ClientListener;
import listener.GameListener;
import core.Card;
import core.Equipment;
import core.Operation;
import core.Player;
import core.PlayerInfo;
import core.Update;
import events.NearDeathEvent;
import events.TurnDiscardOperation;

/**
 * client side complete implementation of player, used as player himself
 * @author Harry
 *
 */
public class PlayerOriginalClientComplete extends PlayerOriginalClientSimple implements ClientListener
{
	//******** in-game properties ***********
	private ArrayList<Card> cardsOnHand;
	private int attackLimit;//limit of attacks can be used in one TURN_DEAL, by default 1
	private int attackUsed;//number of attacks already used this TURN_DEAL
	private int wineLimit;//limit of wines can be used in on TURN_DEAL, by default 1
	private int wineUsed;//number of wines already used this TURN_DEAL
	private boolean isWineUsed;//whether wine is currently used
	
	private transient GameListener gameListener;
	//private settings
	private transient StageUpdate currentStage;
	private transient ArrayList<PlayerOriginalClientSimple> otherPlayers;
		
		
	//in-game interactive properties
	private Card cardActivated;
	private Operation operation;
	private ArrayList<Card> cardsUsedThisTurn;
	private Client client;
	private Update updateToSend;
	public PlayerOriginalClientComplete(String name, int position) 
	{
		super(name, position);
		init();
	}
	public PlayerOriginalClientComplete(String name, Client client)
	{
		super(name);
		setPosition(0);
		this.client = client;
		init();
	}
	private void init()
	{
		cardsOnHand = new ArrayList<Card>();

		otherPlayers = new ArrayList<PlayerOriginalClientSimple>();
		cardsUsedThisTurn = new ArrayList<Card>();
		//init in-game interactive properties
		cardActivated = null;

		currentStage = null;
		updateToSend = null;
		operation = null;
		
		attackLimit = 1;
		attackUsed = 0;
		wineLimit = 1;
		wineUsed = 0;
		isWineUsed = false;
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
	public void setDeckSize(int size)
	{
		gameListener.onDeckSizeUpdated(size);
	}
	@Override
	public void addCard(Card card)
	{
		cardsOnHand.add(card);
		super.addCard(card);
	}
	@Override
	public void useCard(Card card)
	{
		cardsOnHand.remove(card);
		super.useCard(card);
	}
	@Override
	public void discardCard(Card card)
	{
		cardsOnHand.remove(card);
		super.discardCard(card);
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
	@Override
	public void takeDamage(Damage damage)
	{
		System.out.println("Taking damage "+damage.getAmount());
		super.takeDamage(damage);
		if(isDying())
		{
			client.sendToMaster(new NearDeathEvent(currentStage.getSource(),damage.getSource(),getPlayerInfo(),damage.getNext()));
		}
		else
			client.sendToMaster(damage.getNext());
	}
	/**
	 * <li>{@link GameListener} notified
	 * @param update
	 */
	public void sendToMaster(Update update)
	{
		client.sendToMaster(update);
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
		currentStage.nextStage(this);
		DrawCardsFromDeck update = new DrawCardsFromDeck(getPlayerInfo(),2,currentStage);
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
		disableAll();
//		for(Card card : cardsSelected)
//			gameListener.onCardUnselected(card);
//		cardsSelected.clear();
//		for(Player target : targetsSelected)
//			gameListener.onTargetUnselected(target);
//		targetsSelected.clear();
		currentStage.nextStage(this);
		client.sendToMaster(currentStage);
	}
	@Override
	public void turnDiscard()
	{
		if(getCardsOnHandCount() <= getCardOnHandLimit())
		{
			currentStage.nextStage(this);
			client.sendToMaster(currentStage);
		}
		else
		{
			currentStage.nextStage(this);
			client.sendToMaster(new TurnDiscardOperation(getPlayerInfo(),currentStage));
		}
	}
	@Override
	public void endTurn()
	{
		super.endTurn();
		attackUsed = 0;
		wineUsed = 0;
		isWineUsed = false;
		operation = null;
		cardsUsedThisTurn.clear();
		currentStage.nextStage(this);
		client.sendToMaster(currentStage);
	}
	/**
	 * <li>no card activated
	 * <li>no operation
	 * <li>no card on hand selectable
	 * <li>no target selectable
	 * <li>no button can be pressed
	 */
	public void disableAll()
	{
		cardActivated = null;
		setAllCardsOnHandSelectable(false);
		setAllTargetsSelectableExcludingSelf(false);
		gameListener.onConfirmSetEnabled(false);
		gameListener.onCancelSetEnabled(false);
		gameListener.onEndSetEnabled(false);
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
	public void setAllTargetsSelectableExcludingSelf(boolean selectable)
	{
		for(PlayerOriginal p : otherPlayers)
			if(p.isAlive())
				gameListener.onTargetSetSelectable(p.getPlayerInfo(), selectable);
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
		if(cardActivated == null && operation == null)//no card activated
		{
			operation = card.onActivatedBy(this,currentStage);
			cardActivated = card;
		}
		else if(cardActivated != null)
		{
			operation.onCancelledBy(this);
			operation = null;
			if(cardActivated.equals(card))//cancel
			{
				cardActivated = null;
			}
			else//change
			{
				cardActivated = card;
				operation = cardActivated.onActivatedBy(this,currentStage);
			}
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
	/**
	 * called by user clicking "confirm"
	 */
	public void confirm()
	{
		disableAll();
		Operation temp = operation;
		operation = null;
		temp.onConfirmedBy(this);
	}
	/**
	 * called by user clicking "cancel"
	 * <li>operation.onCancelledBy(this)
	 * <li>operation = null;
	 * <li>cardActivated = null
	 */
	public void cancel()
	{
		cardActivated = null;
		Operation temp = operation;
		operation = null;
		temp.onCancelledBy(this);
	}

	public PlayerInfo getNextPlayerAlive()
	{
		Player next = null;
		for(Player p : otherPlayers)
		{
			if(!p.isAlive())
				continue;
			if(next == null && p.getPosition() > getPosition())
			{
				next = p;
				continue;
			}
			if(p.getPosition() > getPosition() && p.getPosition() < next.getPosition())
				next = p;
		}
		if(next == null)
		{
			for(Player p : otherPlayers)
			{
				if(!p.isAlive())
					continue;
				if(next == null && p.getPosition() < getPosition())
				{
					next = p;
					continue;
				}
				if(p.getPosition() < next.getPosition())
					next = p;
			}
		}
		if(next == null)
			System.err.println("Master: Next player not found");
		return next.getPlayerInfo();
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
	//************** methods related to in-game properties ****************


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
		isWineUsed = true;
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
	
}
