package core.event.game.instants;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.specials.instants.AOEInstantSpecialGameController;

public class GenericAOEInstantSpecialTargetEffectivenessEvent implements AOEInstantSpecialTargetEffectivenessEvent {
	
	private final PlayerCompleteServer target;
	private final AOEInstantSpecialGameController controller;
	
	public GenericAOEInstantSpecialTargetEffectivenessEvent(PlayerCompleteServer target, AOEInstantSpecialGameController controller) {
		this.target = target;
		this.controller = controller;
	}
	
	@Override
	public PlayerCompleteServer getCurrentTarget() {
		return this.target;
	}

	@Override
	public AOEInstantSpecialGameController getController() {
		return this.controller;
	}

}
