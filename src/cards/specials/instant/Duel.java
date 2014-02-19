package cards.specials.instant;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.DuelOperation;



public class Duel extends Instant
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -301334501587512526L;
	public static final String Duel = "Duel";
	public Duel(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return Duel;
	}

	@Override
	public Operation onActivatedBy(PlayerClientComplete player,Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setAllTargetsSelectableExcludingSelf(true);
		player.setCancelEnabled(true);
		return new DuelOperation(player.getPlayerInfo(),player.getCurrentStage().getSource(),this,next);
	}
}
