package cards.specials.instant;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.CreationOperation;

public class Creation extends Instant
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5695736855862242617L;
	public static final String CREATION = "Creation";
	public Creation(int num, Suit suit)
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
}
