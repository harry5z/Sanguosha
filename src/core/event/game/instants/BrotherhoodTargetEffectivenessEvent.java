package core.event.game.instants;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.specials.instants.AOEInstantSpecialGameController;

public class BrotherhoodTargetEffectivenessEvent extends AbstractAOEInstantSpecialTargetEffectivenessEvent {

	public BrotherhoodTargetEffectivenessEvent(PlayerCompleteServer target, AOEInstantSpecialGameController controller) {
		super(target, controller);
	}

}
