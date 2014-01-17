package update;

import player.PlayerOriginalClientComplete;
import core.Framework;
import core.Player;
import core.PlayerInfo;
import events.NearDeathEvent;

public class LossOfHealth implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6030578142962861498L;
	private PlayerInfo target;
	private int amount;
	private Update next;
	
	/**
	 * non-damage loss of health, therefore source-less
	 * @param target : target of damage
	 * @param amount : amount of damage
	 */
	public LossOfHealth(PlayerInfo target, int amount,Update next)
	{
		this.target = target;
		this.amount = amount;
		this.next = next;
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
		if(player.isEqualTo(target))
		{
			player.changeHealthCurrentBy(-amount);
			if(player.isDying())
				player.sendToMaster(new NearDeathEvent(player.getCurrentStage().getSource(),null,target,next));
			else
				player.sendToMaster(next);
		}
		else
		{
			for(Player p : player.getOtherPlayers())
			{
				if(p.isEqualTo(target))
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
