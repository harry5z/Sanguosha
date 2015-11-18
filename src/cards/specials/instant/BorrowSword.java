package cards.specials.instant;

import player.Player;
import player.PlayerComplete;
import cards.equipments.Equipment.EquipmentType;

import commands.Command;
import core.client.game.operations.Operation;

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
