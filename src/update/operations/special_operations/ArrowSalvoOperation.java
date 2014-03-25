package update.operations.special_operations;

import cards.Card;
import cards.basics.Dodge;
import player.PlayerClientComplete;
import update.Update;

public class ArrowSalvoOperation extends AreaOfEffectOperation
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2434095276900355365L;

	public ArrowSalvoOperation(PlayerClientComplete player, Card aoe,Update next) 
	{
		super(player, aoe, next);
	}

	@Override
	protected void AOETargetOperation(PlayerClientComplete target) 
	{
		target.setCardSelectableByName(Dodge.DODGE, true);
		target.getGameListener().onSetMessage("You are targeted by Arrow Salvo, do you use Dodge?");
		target.setCancelEnabled(true);	
	}

	@Override
	public String getName() 
	{
		return "Arrow Salvo";
	}
}
