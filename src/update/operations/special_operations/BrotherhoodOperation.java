package update.operations.special_operations;

import cards.Card;
import player.PlayerClientComplete;
import update.IncreaseOfHealth;
import update.Update;

public class BrotherhoodOperation extends AreaOfEffectOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8220370748719969280L;
	public BrotherhoodOperation(PlayerClientComplete player, Card aoe,Update next)
	{
		super(player, aoe, next);
		currentTarget = source;
	}

	@Override
	protected void playerOpBefore(PlayerClientComplete player)
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
	protected void targetOp(PlayerClientComplete target)
	{
		this.setStage(AFTER);
		target.sendToMaster(new IncreaseOfHealth(source,currentTarget,this));
	}
	@Override
	public String getName()
	{
		return "Brotherhood";
	}
}
