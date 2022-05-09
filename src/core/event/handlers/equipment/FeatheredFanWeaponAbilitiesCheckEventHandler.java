package core.event.handlers.equipment;

import commands.game.client.DecisionUIClientCommand;
import core.event.game.basic.AttackPreAcquisitionWeaponAbilitiesCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Damage.Element;
import core.server.game.Game;
import core.server.game.controllers.equipment.FeatheredFanGameController;
import core.server.game.controllers.mechanics.AttackGameController.AttackStage;
import exceptions.server.game.GameFlowInterruptedException;

public class FeatheredFanWeaponAbilitiesCheckEventHandler extends AbstractEventHandler<AttackPreAcquisitionWeaponAbilitiesCheckEvent> {

	public FeatheredFanWeaponAbilitiesCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackPreAcquisitionWeaponAbilitiesCheckEvent> getEventClass() {
		return AttackPreAcquisitionWeaponAbilitiesCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackPreAcquisitionWeaponAbilitiesCheckEvent event, Game game, ConnectionController connection)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		if (event.controller.getAttackCard().getElement() == Element.NORMAL) {
			game.pushGameController(new FeatheredFanGameController(game, event.controller));
			throw new GameFlowInterruptedException(() -> {
				event.controller.setStage(AttackStage.TARGET_ACQUISITION);
				connection.sendCommandToAllPlayers(new DecisionUIClientCommand(
					this.player.getPlayerInfo(),
					"Use Feathered Fan?"
				));
			});
		}
	}

}
