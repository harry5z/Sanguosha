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
	
	public AbstractPlayerDecisionActionGameController(Game game) {
		super(game);
	}

	@Override
	protected final void handleStage(PlayerDecisionAction stage) throws GameFlowInterruptedException {
		switch (stage) {
			case DECISION_REQUEST:
				this.nextStage();
				this.handleDecisionRequest();
				break;
			case DECISION_CONFIRMATION:
				this.nextStage();
				this.handleDecisionConfirmation();
				break;
			case ACTION:
				this.nextStage();
				this.handleAction();
				break;
			case END:
				break;
		}
	}
	
	@Override
	protected final PlayerDecisionAction getInitialStage() {
		return PlayerDecisionAction.DECISION_REQUEST;
	}
	
	protected abstract void handleDecisionRequest() throws GameFlowInterruptedException;
	
	protected abstract void handleDecisionConfirmation() throws GameFlowInterruptedException;
	
	protected abstract void handleAction() throws GameFlowInterruptedException;

}
