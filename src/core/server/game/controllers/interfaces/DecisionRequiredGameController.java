package core.server.game.controllers.interfaces;

import core.server.game.controllers.GameController;

public interface DecisionRequiredGameController extends GameController {
	
	public void onDecisionMade(boolean confirmed);
	
}
