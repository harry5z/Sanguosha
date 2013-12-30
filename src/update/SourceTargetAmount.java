package update;

import core.Player;
import core.PlayerInfo;
import core.Update;

public abstract class SourceTargetAmount implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -724386714600121367L;
	private PlayerInfo source;
	private PlayerInfo target;
	private int amount;
	
	/**
	 * change the target of damage to player
	 * @param player
	 */
	public void setTarget(PlayerInfo target)
	{
		this.target = target;
	}
	/**
	 * change the source of damage to player
	 * @param player
	 */
	public void setSource(PlayerInfo source)
	{
		this.source = source;
	}
	/**
	 * Require: amount > 0
	 * @param amount
	 */
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
	/**
	 * Require: number != 0
	 * @param number
	 */
	public void changeAmountBy(int number)
	{
		amount += number;
	}
	protected PlayerInfo getSource()
	{
		return source;
	}
	protected PlayerInfo getTarget()
	{
		return target;
	}
	protected int getAmount()
	{
		return amount;
	}
}
