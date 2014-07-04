package commands;

import player.PlayerComplete;
import core.Game;
import core.PlayerInfo;

public class RequireUseOfCardByName extends Command
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4679312323429905803L;
	private PlayerInfo source;
	private PlayerInfo target;
	private String cardName;
	
	public RequireUseOfCardByName(PlayerInfo source,PlayerInfo target,String cardName,Command next)
	{
		super(next);
		this.source = source;
		this.target = target;
		this.cardName = cardName;
	}
	@Override
	public void ServerOperation(Game framework) 
	{
		framework.sendToAllClients(this);
	}

	public void setCardUsed(boolean used)
	{
		
	}
	@Override
	public void ClientOperation(PlayerComplete player) {
		// TODO Auto-generated method stub
		
	}

}
