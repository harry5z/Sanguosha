package events.special_events;

import basics.Dodge;
import player.PlayerOriginalClientComplete;
import update.Update;
import core.Card;

public class ArrowSalvoOperation extends AreaOfEffectOperation
{

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
