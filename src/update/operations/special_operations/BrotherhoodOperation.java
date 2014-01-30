package update.operations.special_operations;

import player.PlayerOriginalClientComplete;
import update.IncreaseOfHealth;
import update.Update;
import core.Card;

public class BrotherhoodOperation extends AreaOfEffectOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8220370748719969280L;
	public BrotherhoodOperation(PlayerOriginalClientComplete player, Card aoe,Update next)
	{
		super(player, aoe, next);
		currentTarget = source;
	}

	@Override
	protected void playerOpBefore(PlayerOriginalClientComplete player)
	{
		if(player.matches(currentTarget))
		{
			if(player.getHealthCurrent() == player.getHealthLimit())//not hurt
				setStage(AFTER);
			else
				setStage(NEUTRALIZATION);
			player.sendToMaster(this);
		}
	}
	@Override
	protected void targetOp(PlayerOriginalClientComplete target)
	{
		this.setStage(AFTER);
		target.sendToMaster(new IncreaseOfHealth(source,currentTarget,this));
	}

}
