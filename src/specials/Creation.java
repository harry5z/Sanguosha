package specials;

import player.PlayerOriginalClientComplete;
import update.Update;
import core.Operation;
import events.special_events.CreationOperation;

public class Creation extends Instant
{
	public static final String CREATION = "Creation";
	public Creation(int num, int suit)
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return CREATION;
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next) 
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new CreationOperation(player.getPlayerInfo(),player.getCurrentStage().getSource(),this,next);
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return true;
	}

}
