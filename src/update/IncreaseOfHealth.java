package update;

import player.PlayerOriginalClientComplete;
import core.Framework;
import core.PlayerInfo;

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
	public IncreaseOfHealth(PlayerInfo source, Update next)
	{
		super(next);
		this.setSource(source);
		this.setTarget(source);
		this.setAmount(1);
	}
	public IncreaseOfHealth(PlayerInfo source, PlayerInfo target, Update next)
	{
		super(next);
		this.setSource(source);
		this.setTarget(target);
		this.setAmount(1);
	}
	@Override
	public void frameworkOperation(Framework framework) 
	{
		//framework.findMatch(target).changeHealthCurrentBy(amount);
		framework.sendToAllClients(this);
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		if(player.matches(getTarget()))
		{
			player.changeHealthCurrentBy(getAmount());
			player.sendToMaster(getNext());
		}
		else
			player.findMatch(getTarget()).changeHealthCurrentBy(getAmount());
	}

}
