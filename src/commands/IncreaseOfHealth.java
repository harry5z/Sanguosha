package commands;

import core.player.PlayerComplete;
import core.player.PlayerInfo;
import core.server.game.Game;

public class IncreaseOfHealth extends SourceTargetAmount
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1257117652694294645L;
	/**
	 * simple setup: source = target, amount = 1
	 * @param source
	 */
	public IncreaseOfHealth(PlayerInfo source, Command next)
	{
		super(next);
		this.setSource(source);
		this.setTarget(source);
		this.setAmount(1);
	}
	public IncreaseOfHealth(PlayerInfo source, PlayerInfo target, Command next)
	{
		super(next);
		this.setSource(source);
		this.setTarget(target);
		this.setAmount(1);
	}
	@Override
	public void ServerOperation(Game framework) 
	{
		//framework.findMatch(target).changeHealthCurrentBy(amount);
		framework.sendToAllClients(this);
	}

	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		if(player.equals(getTarget()))
		{
			player.changeHealthCurrentBy(getAmount());
			player.sendToServer(getNext());
		}
		else
			player.findMatch(getTarget()).changeHealthCurrentBy(getAmount());
	}

}
