package core.server.game.controllers;

import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractPlayerDecisionActionGameController
	extends AbstractGameController<AbstractPlayerDecisionActionGameController.PlayerDecisionAction> {
	
	public static enum PlayerDecisionAction implements GameControllerStage<PlayerDecisionAction> {
		DECISION_REQUEST,
		DECISION_CONFIRMATION,
		ACTION,
		END,
	}

	@Override
	protected final void handleStage(Game game, PlayerDecisionAction stage) throws GameFlowInterruptedException {
		switch (stage) {
			case DECISION_REQUEST:
				this.nextStage();
				this.handleDecisionRequest(game);
				break;
			case DECISION_CONFIRMATION:
				this.nextStage();
				this.handleDecisionConfirmation(game);
				break;
			case ACTION:
				this.nextStage();
				this.handleAction(game);
				break;
			case END:
				break;
		}
	}
	
	@Override
	protected final PlayerDecisionAction getInitialStage() {
		return PlayerDecisionAction.DECISION_REQUEST;
	}
	
	protected abstract void handleDecisionRequest(Game game) throws GameFlowInterruptedException;
	
	protected abstract void handleDecisionConfirmation(Game game) throws GameFlowInterruptedException;
	
	protected abstract void handleAction(Game game) throws GameFlowInterruptedException;

}
