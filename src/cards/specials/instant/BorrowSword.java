package cards.specials.instant;

import cards.equipments.Equipment.EquipmentType;
import core.GameState;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.BorrowSwordOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;

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
	public Operation generateOperation(Activatable source) {
		return new BorrowSwordOperation(source);
	}

	@Override
	public boolean isActivatable(GameState game) {
		for (PlayerSimple player : game.getOtherPlayers()) {
			if (player.isEquipped(EquipmentType.WEAPON)) {
				return true;
			}
		}
		return false;
	}

}
