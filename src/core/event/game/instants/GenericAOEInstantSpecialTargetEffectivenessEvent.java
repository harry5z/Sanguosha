package core.event.game.instants;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.specials.instants.AbstractMultiTargetInstantSpecialGameController;

public class GenericAOEInstantSpecialTargetEffectivenessEvent implements AOEInstantSpecialTargetEffectivenessEvent {
	
	private final PlayerCompleteServer target;
	private final AbstractMultiTargetInstantSpecialGameController controller;
	
	public GenericAOEInstantSpecialTargetEffectivenessEvent(PlayerCompleteServer target, AbstractMultiTargetInstantSpecialGameController controller) {
		this.target = target;
		this.controller = controller;
	}
	
	@Override
	public PlayerCompleteServer getCurrentTarget() {
		return this.target;
	}

	@Override
	public AbstractMultiTargetInstantSpecialGameController getController() {
		return this.controller;
	}

}
