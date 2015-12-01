package cards.specials.instant;

import cards.equipments.Equipment.EquipmentType;
import core.client.ClientGameInfo;
import core.client.game.operations.Operation;
import core.player.PlayerSimple;

public class BorrowSword extends Instant {

	private static final long serialVersionUID = -8537939550303913600L;

	public BorrowSword(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Borrow Sword";
	}

	@Override
	public Operation generateOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatable(ClientGameInfo game) {
		for (PlayerSimple player : game.getOtherPlayers()) {
			if (player.isEquipped(EquipmentType.WEAPON)) {
				return true;
			}
		}
		return false;
	}

}
