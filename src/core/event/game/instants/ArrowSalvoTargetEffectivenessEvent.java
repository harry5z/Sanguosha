package core.event.game.instants;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.specials.instants.AbstractMultiTargetInstantSpecialGameController;

public class ArrowSalvoTargetEffectivenessEvent extends GenericAOEInstantSpecialTargetEffectivenessEvent {

	public ArrowSalvoTargetEffectivenessEvent(PlayerCompleteServer target, AbstractMultiTargetInstantSpecialGameController controller) {
		super(target, controller);
	}

}
