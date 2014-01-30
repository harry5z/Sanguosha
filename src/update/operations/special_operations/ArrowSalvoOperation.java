package update.operations.special_operations;

import basics.Dodge;
import player.PlayerOriginalClientComplete;
import update.Update;
import core.Card;

public class ArrowSalvoOperation extends AreaOfEffectOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2434095276900355365L;

	public ArrowSalvoOperation(PlayerOriginalClientComplete player, Card aoe,Update next) 
	{
		super(player, aoe, next);
	}

	@Override
	protected void targetOp(PlayerOriginalClientComplete target) 
	{
		target.setCardSelectableByName(Dodge.DODGE, true);
		target.setCancelEnabled(true);
		target.setOperation(this);		
	}

}
