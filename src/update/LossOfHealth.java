package update;

import core.Player;
import core.Update;

public class LossOfHealth extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6030578142962861498L;
	private Player target;
	private int amount;
	
	/**
	 * non-damage loss of health, therefore source-less
	 * @param target : target of damage
	 * @param amount : amount of damage
	 */
	public LossOfHealth(Player target, int amount)
	{
		this.target = target;
		this.amount = amount;
	}
	
	public Player getTarget()
	{
		return target;
	}
	
	public int getAmount()
	{
		return amount;
	}

	@Override
	public void playerOperation(Player player)
	{
		if(target.equals(player))
		{
			player.changeHealthCurrentBy(-amount);
		}
		else
		{
			for(Player p : player.getOtherPlayers())
			{
				if(p.equals(player))
				{
					p.changeHealthCurrentBy(-amount);
					return;
				}
			}
		}
	}

}
