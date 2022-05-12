package core.event.game.instants;

import core.server.game.controllers.specials.instants.AbstractMultiTargetInstantSpecialGameController;

public interface AOEInstantSpecialTargetEffectivenessEvent extends AOETargetEffectivenessEvent {
	
	public AbstractMultiTargetInstantSpecialGameController getController();
}
