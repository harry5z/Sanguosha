package commands.operations.special;

import cards.Card;
import cards.basics.Dodge;

import commands.Command;
import core.player.PlayerComplete;

public class ArrowSalvoOperation extends AreaOfEffectOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2434095276900355365L;

	public ArrowSalvoOperation(PlayerComplete player, Card aoe,Command next) 
	{
		super(player, aoe, next);
	}

	@Override
	protected void AOETargetOperation(PlayerComplete target) 
	{
		target.setCardSelectableByName(Dodge.DODGE, true);
		target.getGameListener().setMessage("You are targeted by Arrow Salvo, do you use Dodge?");
		target.getGameListener().setCancelEnabled(true);
	}

	@Override
	public String getName() 
	{
		return "Arrow Salvo";
	}
}
