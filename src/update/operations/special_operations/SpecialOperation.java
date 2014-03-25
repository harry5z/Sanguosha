package update.operations.special_operations;

import cards.Card;
import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;
import core.Framework;
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
	
	public SpecialOperation(Update next, PlayerInfo turnPlayer)
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
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	public abstract PlayerInfo getCurrentTarget();

	protected void cardSelectedAsReaction(PlayerClientComplete operator, Card card)
	{
		if(reactionCard != null)//unselect previous
		{
			operator.setCardOnHandSelected(reactionCard, false);
			if(reactionCard.equals(card))//unselect
			{
				reactionCard = null;
				operator.setConfirmEnabled(false);
			}
			else//change
			{
				reactionCard = card;
				operator.setCardOnHandSelected(card, true);
			}
		}
		else //select new
		{
			reactionCard = card;
			operator.setCardOnHandSelected(card, true);
			operator.setConfirmEnabled(true);
		}
	}
	public PlayerInfo getCurrentPlayer()
	{
		return currentPlayer;
	}
	@Override
	public void playerOperation(PlayerClientComplete player)
	{
		if(stage == NEUTRALIZATION && player.matches(turnPlayer))
		{
			if(neutralizable)
				player.sendToMaster(new NeutralizationOperation(this,turnPlayer));
			else
			{
				currentPlayer = turnPlayer;
				stage = EFFECT;
				player.sendToMaster(this);
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
	protected void playerOpBefore(PlayerClientComplete player)
	{
		if(player.matches(currentPlayer))
			sendToNextPlayer(player);
	}
	/**
	 * Player's operation when special card is in effect
	 * @param player : target of special card
	 */
	protected abstract void playerOpEffect(PlayerClientComplete player);
	/**
	 * Player's operation after special card takes effect<br>
	 * Default ending behavior executed by CurrentPlayer: continue with next operation<br>
	 * Note that when a special operation is Neutralized, it continues to here
	 * @param player
	 */
	protected void playerOpAfter(PlayerClientComplete player)
	{
		if(player.matches(currentPlayer))
			player.sendToMaster(getNext());
	}
	private void sendToNextPlayer(PlayerClientComplete player)
	{
		currentPlayer = player.getNextPlayerAlive();
		if(currentPlayer.equals(turnPlayer))//circle complete
		{
			stage++;
		}
		player.sendToMaster(this);
	}
	/**
	 * name of this operation
	 * @return name
	 */
	public abstract String getName();
}
