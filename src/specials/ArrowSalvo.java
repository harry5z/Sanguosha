package specials;

import player.PlayerOriginalClientComplete;
import update.Update;
import core.Operation;
import events.special_events.ArrowSalvoOperation;


public class ArrowSalvo extends Instant
{

	public ArrowSalvo(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Arrow_Salvo";
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
