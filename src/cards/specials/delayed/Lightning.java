package cards.specials.delayed;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;

public class Lightning extends Delayed
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8502796453884676779L;

	public Lightning(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Lightning";
	}

	@Override
	public Operation onActivatedBy(PlayerClientComplete player,
			Update next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerClientComplete player) {
		//if(player.canBeTargetedBy(this);
		return true;
	}

}
