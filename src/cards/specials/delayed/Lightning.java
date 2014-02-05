package cards.specials.delayed;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;

public class Lightning extends Delayed
{
	public Lightning(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Lightning";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,
			Update next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) {
		//if(player.canBeTargetedBy(this);
		return true;
	}

}
