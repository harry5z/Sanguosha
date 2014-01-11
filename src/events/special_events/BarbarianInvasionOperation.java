package events.special_events;

import basics.Attack;
import player.PlayerOriginalClientComplete;
import update.Update;
import core.Card;

public class BarbarianInvasionOperation extends AreaOfEffectOperation
{

	public BarbarianInvasionOperation(PlayerOriginalClientComplete player,Card barbarianInvasion, Update next) 
	{
		super(player, barbarianInvasion, next);
	}

	@Override
	protected void targetOp(PlayerOriginalClientComplete target) 
	{
		target.setCardSelectableByName(Attack.ATTACK, true);
		target.setCancelEnabled(true);
		target.setOperation(this);
	}

}
