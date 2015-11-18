package commands;

import player.PlayerComplete;
import cards.Card;

import commands.operations.decision.DecisionOperation;
import core.PlayerInfo;
import core.server.Game;

public class Decision extends Command
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3876772467662191549L;
	private PlayerInfo source;
	private Card decision;
	
	public Decision(PlayerInfo source, DecisionOperation next)
	{
		super(next);
		this.source = source;
	}
	@Override
	public void ServerOperation(Game framework)
	{
		decision = framework.getDeck().draw();
		framework.sendToAllClients(this);
	}

	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		player.showCard(decision);
		if(player.equals(source))
		{
			((DecisionOperation)getNext()).setResult(decision);
			player.sendToServer(getNext());
		}
	}

}
