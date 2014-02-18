package update;

import player.Player;
import player.PlayerOriginalClientComplete;
import update.operations.NearDeathOperation;
import core.Framework;
import core.PlayerInfo;

public class LossOfHealth extends Update
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
	public LossOfHealth(PlayerInfo target, int amount,Update next)
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
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		if(player.matches(target))
		{
			player.changeHealthCurrentBy(-amount);
			if(player.isDying())
				player.sendToMaster(new NearDeathOperation(player.getCurrentStage().getSource(),null,target,getNext()));
			else
				player.sendToMaster(getNext());
		}
		else
		{
			for(Player p : player.getOtherPlayers())
			{
				if(p.matches(target))
				{
					p.changeHealthCurrentBy(-amount);
					return;
				}
			}
		}
	}

	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.sendToAllClients(this);
	}

}
