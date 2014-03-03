package cards.specials.instant;

import cards.equipments.Equipment.EquipmentType;
import player.Player;
import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;

public class BorrowSword extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8537939550303913600L;

	public BorrowSword(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Borrow Sword";
	}

	@Override
	public Operation onActivatedBy(PlayerClientComplete player,Update next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerClientComplete player) {
		for(Player p : player.getOtherPlayers())
			if(p.isEquipped(EquipmentType.WEAPON))
				return true;
		return false;
	}

}
