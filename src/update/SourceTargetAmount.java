package update;

import core.Player;

public abstract class SourceTargetAmount extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -724386714600121367L;
	private Player source;
	private Player target;
	private int amount;
	
	/**
	 * change the target of damage to player
	 * @param player
	 */
	public void setTarget(Player target)
	{
		this.target = target;
	}
	/**
	 * change the source of damage to player
	 * @param player
	 */
	public void setSource(Player source)
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
	protected Player getSource()
	{
		return source;
	}
	protected Player getTarget()
	{
		return target;
	}
	protected int getAmount()
	{
		return amount;
	}
}
