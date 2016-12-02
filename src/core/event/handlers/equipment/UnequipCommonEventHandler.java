package core.event.handlers.equipment;

import cards.equipments.Equipment;
import core.event.game.UnequipEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class UnequipCommonEventHandler extends AbstractEventHandler<UnequipEvent> {

	public UnequipCommonEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<UnequipEvent> getEventClass() {
		return UnequipEvent.class;
	}

	@Override
	protected void handleIfActivated(UnequipEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.equals(event.player)) {
			return;
		}
		if (this.player.isEquipped(event.equipmentType)) {
			try {
				Equipment old = this.player.getEquipment(event.equipmentType);
				this.player.unequip(event.equipmentType);
				old.onUnequipped(game, player);
			} catch (InvalidPlayerCommandException e) {
				// Should not happen
				throw new RuntimeException("cannot unequip");
			}
		}
	}

}
