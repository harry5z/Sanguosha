package core.server.game.controllers;

public interface DecisionRequiredGameController extends GameController {
	
	public void onDecisionMade(boolean confirmed);
	
}
