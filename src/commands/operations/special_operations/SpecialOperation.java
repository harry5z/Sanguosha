package commands.operations.special_operations;

import commands.Command;
import commands.operations.Operation;
import player.PlayerComplete;
import cards.Card;
import core.Game;
import core.PlayerInfo;

public abstract class SpecialOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4037333002465107383L;
	protected static final byte BEFORE = 1;
	protected static final byte NEUTRALIZATION = 2;
	protected static final byte EFFECT = 3;
	protected static final byte AFTER = 4;
	private boolean neutralizable;
	
	private int stage;
	private PlayerInfo turnPlayer;
	private PlayerInfo currentPlayer;

	protected Card reactionCard;
	
	public SpecialOperation(Command next, PlayerInfo turnPlayer)
	{
		super(next);
		neutralizable = true;
		stage = BEFORE;
		this.turnPlayer = turnPlayer;
		this.currentPlayer = turnPlayer;
		reactionCard = null;
	}
	public void setNeutralizable(boolean neutralizable)
	{
		this.neutralizable = neutralizable;
	}
	public boolean isNeutralizable()
	{
		return neutralizable;
	}
	public void nextStage()
	{
		stage++;
	}
	protected int getStage()
	{
		return stage;
	}
	public void setStage(int stage)
	{
		this.stage = stage;
	}
	@Override
	public void ServerOperation(Game framework) 
	{
		framework.sendToAllClients(this);
	}

	public abstract PlayerInfo getCurrentTarget();

	/**
	 * select a card as the card in reaction
	 * @param operator
	 * @param card
	 */
	protected void cardSelectedAsReaction(PlayerComplete operator, Card card)
	{
		if(reactionCard != null)//unselect previous
		{
			operator.getGameListener().setCardSelected(reactionCard, false);
			if(reactionCard.equals(card))//unselect
			{
				reactionCard = null;
				operator.getGameListener().setConfirmEnabled(false);
			}
			else//change
			{
				reactionCard = card;
				operator.getGameListener().setCardSelected(card, true);
			}
		}
		else //select new
		{
			reactionCard = card;
			operator.getGameListener().setCardSelected(card, true);
			operator.getGameListener().setConfirmEnabled(true);
		}
	}
	public PlayerInfo getCurrentPlayer()
	{
		return currentPlayer;
	}
	@Override
	public void ClientOperation(PlayerComplete player)
	{
		if(stage == NEUTRALIZATION && player.equals(turnPlayer))
		{
			if(neutralizable)
				player.sendToServer(new NeutralizationOperation(this,turnPlayer));
			else
			{
				currentPlayer = turnPlayer;
				stage = EFFECT;
				player.sendToServer(this);
			}	
			return;
		}
		else if(stage == EFFECT)
		{
			playerOpEffect(player);
		}
		else if(stage == BEFORE)
			playerOpBefore(player);
		else if (stage == AFTER)
			playerOpAfter(player);
		
	}
	/**
	 * Player's operation before special card is in effect<br>
	 * (Currently just sending to next player because no skills are added yet)
	 * @param player : target of special card
	 */
	protected void playerOpBefore(PlayerComplete player)
	{
		if(player.equals(currentPlayer))
			sendToNextPlayer(player);
	}
	/**
	 * Player's operation when special card is in effect
	 * @param player : target of special card
	 */
	protected abstract void playerOpEffect(PlayerComplete player);
	/**
	 * Player's operation after special card takes effect<br>
	 * Default ending behavior executed by CurrentPlayer: continue with next operation<br>
	 * Note that when a special operation is Neutralized, it continues to here
	 * @param player
	 */
	protected void playerOpAfter(PlayerComplete player)
	{
		if(player.equals(currentPlayer))
			player.sendToServer(getNext());
	}
	private void sendToNextPlayer(PlayerComplete player)
	{
		currentPlayer = player.getNextPlayerAlive();
		if(currentPlayer.equals(turnPlayer))//circle complete
		{
			stage++;
		}
		player.sendToServer(this);
	}
	/**
	 * name of this operation
	 * @return name
	 */
	public abstract String getName();
	
	/**
	 * Cancel the operation, which includes
	 * <ul>
	 * <li> set card unselected </li>
	 * <li> set confirm disabled </li>
	 * <li> set cancel disabled </li>
	 * <li> set operation to null </li>
	 * </ul>
	 * @param player
	 * @param card
	 */
	protected void cancelOperation(PlayerComplete player, Card card)
	{
		player.getGameListener().setCardSelected(card, false);
		player.getGameListener().setConfirmEnabled(false);
		player.getGameListener().setCancelEnabled(false);
		player.setOperation(null);
	}
}
