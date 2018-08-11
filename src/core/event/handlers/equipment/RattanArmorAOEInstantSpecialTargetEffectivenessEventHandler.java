package core.event.handlers.equipment;

import core.event.game.instants.AOEInstantSpecialTargetEffectivenessEvent;
import core.event.game.instants.ArrowSalvoTargetEffectivenessEvent;
import core.event.game.instants.BarbarianInvasionTargetEffectivenessEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.specials.SpecialGameController.SpecialStage;
import exceptions.server.game.GameFlowInterruptedException;

public class RattanArmorAOEInstantSpecialTargetEffectivenessEventHandler extends AbstractEventHandler<AOEInstantSpecialTargetEffectivenessEvent> {

	public RattanArmorAOEInstantSpecialTargetEffectivenessEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AOEInstantSpecialTargetEffectivenessEvent> getEventClass() {
		return AOEInstantSpecialTargetEffectivenessEvent.class;
	}

	@Override
	protected void handleIfActivated(AOEInstantSpecialTargetEffectivenessEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.equals(event.getCurrentTarget())) {
			return;
		}
		
		// only affects Barbarian Invasion & Arrow Salvo
		if (!(event instanceof BarbarianInvasionTargetEffectivenessEvent || event instanceof ArrowSalvoTargetEffectivenessEvent)) {
			return;
		}
		
		throw new GameFlowInterruptedException(() -> {
			event.getController().setStage(SpecialStage.EFFECT_TAKEN);
			event.getController().proceed();
		});
	}

}
