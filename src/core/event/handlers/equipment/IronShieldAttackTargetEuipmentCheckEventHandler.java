package core.event.handlers.equipment;

import cards.Card.Color;
import core.event.game.basic.AttackTargetEquipmentCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.mechanics.AttackResolutionGameController.AttackResolutionStage;
import exceptions.server.game.GameFlowInterruptedException;

public class IronShieldAttackTargetEuipmentCheckEventHandler extends AbstractEventHandler<AttackTargetEquipmentCheckEvent> {

	public IronShieldAttackTargetEuipmentCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackTargetEquipmentCheckEvent> getEventClass() {
		return AttackTargetEquipmentCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackTargetEquipmentCheckEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.getPlayerInfo().equals(event.getTarget())) {
			return;
		}
		
		// block BLACK attacks only
		if (event.getAttackCard().getColor() != Color.BLACK) {
			return;
		}
		
		throw new GameFlowInterruptedException(() -> {
			event.getController().setStage(AttackResolutionStage.END);
			event.getController().proceed();
		});
	}

}
