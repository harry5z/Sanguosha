package core.event.game.instants;

import core.server.game.controllers.specials.instants.AOEInstantSpecialGameController;

public interface AOEInstantSpecialTargetEffectivenessEvent extends AOETargetEffectivenessEvent {
	
	public AOEInstantSpecialGameController getController();
}
