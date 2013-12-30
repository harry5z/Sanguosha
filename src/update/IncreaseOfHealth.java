package update;

import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;
import core.Update;

public class IncreaseOfHealth implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1257117652694294645L;
	private PlayerInfo source;
	private PlayerInfo target;
	private int amount;
	/**
	 * simple setup: source = target, amount = 1
	 * @param source
	 */
	public IncreaseOfHealth(PlayerInfo source)
	{
		this.source = source;
		this.target = source;
		this.amount = 1;
	}
	public void setTarget(PlayerInfo target)
	{
		this.target = target;
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		framework.findMatch(target).changeHealthCurrentBy(amount);
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(target))
			player.changeHealthCurrentBy(amount);
		else
			player.findMatch(target).changeHealthCurrentBy(amount);
	}

}