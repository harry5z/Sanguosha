package specials;

import player.Player;
import player.PlayerOriginalClientComplete;
import update.Update;
import core.Operation;

public class BorrowSword extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8537939550303913600L;

	public BorrowSword(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Borrow Sword";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) {
		for(Player p : player.getOtherPlayers())
			if(p.isEquippedWeapon())
				return true;
		return false;
	}

}
