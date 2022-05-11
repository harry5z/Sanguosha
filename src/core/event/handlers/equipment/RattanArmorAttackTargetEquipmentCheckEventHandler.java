package core.event.handlers.equipment;

import core.event.game.basic.AttackTargetEquipmentCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Damage.Element;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController.AttackResolutionStage;
import exceptions.server.game.GameFlowInterruptedException;

public class RattanArmorAttackTargetEquipmentCheckEventHandler extends AbstractEventHandler<AttackTargetEquipmentCheckEvent> {

	public RattanArmorAttackTargetEquipmentCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackTargetEquipmentCheckEvent> getEventClass() {
		return AttackTargetEquipmentCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackTargetEquipmentCheckEvent event, Game game) throws GameFlowInterruptedException {
		if (!this.player.getPlayerInfo().equals(event.getTarget())) {
			return;
		}
		
		// block NORMAL attacks only
		if (event.getAttackCard().getElement() != Element.NORMAL) {
			return;
		}
		
		game.pushGameController(new AbstractSingleStageGameController(game) {
			@Override
			protected void handleOnce() throws GameFlowInterruptedException {
				event.getController().setStage(AttackResolutionStage.END);
			}
		});
	}

}
