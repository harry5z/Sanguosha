package core.event.game.instants;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.specials.instants.AOEInstantSpecialGameController;

public class BarbarianInvasionTargetEffectivenessEvent extends AbstractAOEInstantSpecialTargetEffectivenessEvent {

	public BarbarianInvasionTargetEffectivenessEvent(PlayerCompleteServer target, AOEInstantSpecialGameController controller) {
		super(target, controller);
	}
	
}
