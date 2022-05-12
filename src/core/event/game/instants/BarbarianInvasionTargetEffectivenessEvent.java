package core.event.game.instants;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.specials.instants.AbstractMultiTargetInstantSpecialGameController;

public class BarbarianInvasionTargetEffectivenessEvent extends GenericAOEInstantSpecialTargetEffectivenessEvent {

	public BarbarianInvasionTargetEffectivenessEvent(PlayerCompleteServer target, AbstractMultiTargetInstantSpecialGameController controller) {
		super(target, controller);
	}
	
}
