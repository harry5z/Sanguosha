package update.operations.special_operations;

import basics.Attack;
import player.PlayerOriginalClientComplete;
import update.Update;
import core.Card;

public class BarbarianInvasionOperation extends AreaOfEffectOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344213304608489554L;

	public BarbarianInvasionOperation(PlayerOriginalClientComplete player,Card barbarianInvasion, Update next) 
	{
		super(player, barbarianInvasion, next);
	}

	@Override
	protected void targetOp(PlayerOriginalClientComplete target) 
	{
		target.setCardSelectableByName(Attack.ATTACK, true);
		target.setCardSelectableByName(Attack.FIRE_ATTACK, true);
		target.setCardSelectableByName(Attack.THUNDER_ATTACK, true);
		target.getGameListener().onSetMessage("You are targeted by Barbarian Invasion, do you use Attack?");
		target.setCancelEnabled(true);
		target.setOperation(this);
	}
	@Override
	public String getName()
	{
		return "Barbarian Invasion";
	}
}
