package core.event.handlers.equipment;

import cards.Card.Color;
import core.event.game.basic.AttackTargetEquipmentCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameDriver;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
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
	protected void handleIfActivated(AttackTargetEquipmentCheckEvent event, GameDriver game) throws GameFlowInterruptedException {
		if (!this.player.getPlayerInfo().equals(event.getTarget())) {
			return;
		}
		
		// block BLACK attacks only
		if (event.getAttackCard().getColor() != Color.BLACK) {
			return;
		}
		
		game.pushGameController(new AbstractSingleStageGameController() {
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				// skip Attack Resolution
				event.getController().setStage(AttackResolutionStage.END);
				game.log(BattleLog.playerAEquipmentPassivelyTriggered(player, player.getShield(), "Attack is blocked"));
			}
		});
	}

}
