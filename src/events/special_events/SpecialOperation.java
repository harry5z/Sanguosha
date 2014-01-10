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
	private static final int BEFORE = 1;
	private static final int NEUTRALIZATION = 2;
	private static final int EFFECT = 3;
	private static final int AFTER = 4;
	private boolean neutralizable;
	
	private Update next;
	private int stage;
	private PlayerInfo turnPlayer;
	private PlayerInfo currentPlayer;

	
	public SpecialOperation(Update next, PlayerInfo turnPlayer)
	{
		neutralizable = true;
		stage = BEFORE;
		this.next = next;
		this.turnPlayer = turnPlayer;
		this.currentPlayer = turnPlayer;
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
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
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
		else if(player.isEqualTo(currentPlayer))
		{
			if(stage == BEFORE)
				playerOpBefore(player);
			else if (stage == AFTER && player.isEqualTo(turnPlayer))
				player.sendToMaster(next);
		}
	}
	protected void playerOpBefore(PlayerOriginalClientComplete player)
	{
		sendToNextPlayer(player);
	}
	protected abstract void playerOpEffect(PlayerOriginalClientComplete player);
	private void sendToNextPlayer(PlayerOriginalClientComplete player)
	{
		currentPlayer = player.getNextPlayerAlive();
		if(currentPlayer.getPosition() == turnPlayer.getPosition())//circle complete
		{
			stage++;
		}
		player.sendToMaster(this);
	}
}
