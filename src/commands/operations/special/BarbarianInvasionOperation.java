package commands.operations.special;

import player.PlayerComplete;
import cards.Card;
import cards.basics.Attack;

import commands.Command;

public class BarbarianInvasionOperation extends AreaOfEffectOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344213304608489554L;

	public BarbarianInvasionOperation(PlayerComplete player,Card barbarianInvasion, Command next) 
	{
		super(player, barbarianInvasion, next);
	}

	@Override
	protected void AOETargetOperation(PlayerComplete target) 
	{
		target.setCardSelectableByName(Attack.ATTACK, true);
		target.setCardSelectableByName(Attack.FIRE_ATTACK, true);
		target.setCardSelectableByName(Attack.THUNDER_ATTACK, true);
		target.getGameListener().setMessage("You are targeted by Barbarian Invasion, do you use Attack?");
		target.getGameListener().setCancelEnabled(true);
	}

	@Override
	public String getName() 
	{
		return "Brotherhood";
	}
}
