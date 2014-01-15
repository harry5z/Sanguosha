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
	private Update nextEvent;
	/**
	 * simple setup: source = target, amount = 1
	 * @param source
	 */
	public IncreaseOfHealth(PlayerInfo source, Update next)
	{
		this.setSource(source);
		this.setTarget(source);
		this.setAmount(1);
		nextEvent = next;
	}
	public IncreaseOfHealth(PlayerInfo source, PlayerInfo target, Update next)
	{
		this.setSource(source);
		this.setTarget(target);
		this.setAmount(1);
		nextEvent = next;
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
		if(player.isEqualTo(getTarget()))
		{
			player.changeHealthCurrentBy(getAmount());
			player.sendToMaster(nextEvent);
		}
		else
			player.findMatch(getTarget()).changeHealthCurrentBy(getAmount());
	}

}
