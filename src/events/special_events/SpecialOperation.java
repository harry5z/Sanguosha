package events.special_events;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import specials.Neutralization;
import update.Update;
import core.Card;
import core.Framework;
import core.Operation;
import core.PlayerInfo;

public abstract class SpecialOperation implements Operation
{
	protected static final int BEFORE = 1;
	protected static final int NEUTRALIZATION = 2;
	protected static final int EFFECT = 3;
	protected static final int AFTER = 4;
	private boolean neutralizable;
	
	private Update next;
	private int stage;
	private PlayerInfo turnPlayer;
	private PlayerInfo currentPlayer;

	protected Card reactionCard;
	
	public SpecialOperation(Update next, PlayerInfo turnPlayer)
	{
		neutralizable = true;
		stage = BEFORE;
		this.next = next;
		this.turnPlayer = turnPlayer;
		this.currentPlayer = turnPlayer;
		reactionCard = null;
	}
	protected Update getNext()
	{
		return next;
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
	public void setStage(int stage)
	{
		this.stage = stage;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

	protected void cardSelectedAsReaction(PlayerOriginalClientComplete operator, Card card)
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
	@Override
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		if(stage == NEUTRALIZATION && player.isEqualTo(turnPlayer))
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
	protected void playerOpBefore(PlayerOriginalClientComplete player)
	{
		if(player.isEqualTo(currentPlayer))
			sendToNextPlayer(player);
	}
	protected abstract void playerOpEffect(PlayerOriginalClientComplete player);
	/**
	 * Default ending behavior executed by CurrentPlayer: continue with next operation
	 * @param player
	 */
	protected void playerOpAfter(PlayerOriginalClientComplete player)
	{
		if(player.isEqualTo(currentPlayer))
			player.sendToMaster(next);
	}
	private void sendToNextPlayer(PlayerOriginalClientComplete player)
	{
		currentPlayer = player.getNextPlayerAlive();
		if(currentPlayer.equals(turnPlayer))//circle complete
		{
			stage++;
		}
		player.sendToMaster(this);
	}
}
