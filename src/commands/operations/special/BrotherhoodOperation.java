package commands.operations.special;

import player.PlayerComplete;
import cards.Card;

import commands.Command;
import commands.IncreaseOfHealth;

public class BrotherhoodOperation extends AreaOfEffectOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8220370748719969280L;
	public BrotherhoodOperation(PlayerComplete player, Card aoe,Command next)
	{
		super(player, aoe, next);
		currentTarget = source;
	}

	@Override
	protected void playerOpBefore(PlayerComplete player)
	{
		if(player.equals(currentTarget))
		{
			if(player.getHealthCurrent() == player.getHealthLimit())//not hurt
				setStage(AFTER);
			else
				setStage(NEUTRALIZATION);
			player.sendToServer(this);
		}
	}
	@Override
	protected void AOETargetOperation(PlayerComplete target)
	{
		this.setStage(AFTER);
		target.sendToServer(new IncreaseOfHealth(source,currentTarget,this));
	}

	@Override
	public String getName()
	{
		return "Brotherhood";
	}
}
