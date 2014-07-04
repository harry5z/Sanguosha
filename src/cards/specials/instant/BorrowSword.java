package cards.specials.instant;

import commands.Command;
import commands.operations.Operation;
import cards.equipments.Equipment.EquipmentType;
import player.Player;
import player.PlayerComplete;

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
	public boolean isActivatableBy(PlayerComplete player) {
		for(Player p : player.getOtherPlayers())
			if(p.isEquipped(EquipmentType.WEAPON))
				return true;
		return false;
	}

	@Override
	protected Operation createOperation(PlayerComplete player, Command next) {
		// TODO Auto-generated method stub
		return null;
	}

}
