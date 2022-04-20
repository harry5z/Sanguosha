package core.event.handlers.hero;

import cards.equipments.Equipment.EquipmentType;
import commands.game.client.DecisionUIClientCommand;
import core.event.game.DodgeArbitrationEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.equipment.TaichiFormationGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class ZhugeliangTaichiDodgeArbitrationEventHandler extends AbstractEventHandler<DodgeArbitrationEvent> {
	
	public ZhugeliangTaichiDodgeArbitrationEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<DodgeArbitrationEvent> getEventClass() {
		return DodgeArbitrationEvent.class;
	}

	@Override
	protected void handleIfActivated(DodgeArbitrationEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.getPlayerInfo().equals(event.getTarget())) {
			return;
		}
		
		if (this.player.isEquipped(EquipmentType.SHIELD)) {
			return;
		}
		
		game.pushGameController(new TaichiFormationGameController(game, this.player));
		connection.sendCommandToAllPlayers(new DecisionUIClientCommand(
			this.player.getPlayerInfo(),
			"Use Taichi Formation?"
		));
		throw new GameFlowInterruptedException();
	}
}
