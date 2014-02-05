package specials;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.special_operations.ArrowSalvoOperation;
import core.Operation;


public class ArrowSalvo extends Instant
{

	public ArrowSalvo(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Arrow Salvo";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next) 
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new ArrowSalvoOperation(player,this,next);
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return true;
	}

}
