package cards.specials.delayed;

import commands.Command;
import commands.operations.Operation;
import player.PlayerComplete;

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
	public Operation onActivatedBy(PlayerComplete player,
			Command next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerComplete player) {
		//if(player.canBeTargetedBy(this);
		return true;
	}

}
