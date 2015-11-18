package commands;

import player.Player;
import player.PlayerComplete;

import commands.operations.NearDeathOperation;
import core.PlayerInfo;
import core.server.Game;

public class LossOfHealth extends Command
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6030578142962861498L;
	private PlayerInfo target;
	private int amount;
	
	/**
	 * non-damage loss of health, therefore source-less
	 * @param target : target of damage
	 * @param amount : amount of damage
	 */
	public LossOfHealth(PlayerInfo target, int amount,Command next)
	{
		super(next);
		this.target = target;
		this.amount = amount;
	}
	
	public PlayerInfo getTarget()
	{
		return target;
	}
	
	public int getAmount()
	{
		return amount;
	}

	@Override
	public void ClientOperation(PlayerComplete player)
	{
		if(player.equals(target))
		{
			player.changeHealthCurrentBy(-amount);
			if(player.isDying())
				player.sendToServer(new NearDeathOperation(player.getCurrentStage().getSource(),null,target,getNext()));
			else
				player.sendToServer(getNext());
		}
		else
		{
			for(Player p : player.getOtherPlayers())
			{
				if(p.equals(target))
				{
					p.changeHealthCurrentBy(-amount);
					return;
				}
			}
		}
	}

	@Override
	public void ServerOperation(Game framework) 
	{
		framework.sendToAllClients(this);
	}

}
