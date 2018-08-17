package core.event.game.instants;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.specials.instants.AOEInstantSpecialGameController;

public class ArrowSalvoTargetEffectivenessEvent extends GenericAOEInstantSpecialTargetEffectivenessEvent {

	public ArrowSalvoTargetEffectivenessEvent(PlayerCompleteServer target, AOEInstantSpecialGameController controller) {
		super(target, controller);
	}

}
