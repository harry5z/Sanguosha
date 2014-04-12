package update.operations.special_operations;

import cards.Card;
import cards.basics.Attack;
import player.PlayerClientComplete;
import update.Update;

public class BarbarianInvasionOperation extends AreaOfEffectOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344213304608489554L;

	public BarbarianInvasionOperation(PlayerClientComplete player,Card barbarianInvasion, Update next) 
	{
		super(player, barbarianInvasion, next);
	}

	@Override
	protected void AOETargetOperation(PlayerClientComplete target) 
	{
		target.setCardSelectableByName(Attack.ATTACK, true);
		target.setCardSelectableByName(Attack.FIRE_ATTACK, true);
		target.setCardSelectableByName(Attack.THUNDER_ATTACK, true);
		target.getGameListener().setMessage("You are targeted by Barbarian Invasion, do you use Attack?");
		target.setCancelEnabled(true);
	}

	@Override
	public String getName() 
	{
		return "Brotherhood";
	}
}
