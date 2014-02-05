package cards.specials.instant;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.DuelOperation;



public class Duel extends Instant
{
	public static final String Duel = "Duel";
	public Duel(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return Duel;
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setAllTargetsSelectableExcludingSelf(true);
		player.setCancelEnabled(true);
		return new DuelOperation(player.getPlayerInfo(),player.getCurrentStage().getSource(),this,next);
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player)
	{
		return true;
	}

}
