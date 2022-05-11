package core.server.game.controllers;

import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractSingleStageGameController extends AbstractGameController<AbstractSingleStageGameController.GenericGameControllerStage> {

	/**
	 * Used for GameControllers which have only 1 Stage
	 */
	public static enum GenericGameControllerStage implements GameControllerStage<GenericGameControllerStage> {
		START,
		END;
	}
	
	public AbstractSingleStageGameController(Game game) {
		super(game);
	}
	
	@Override
	protected final GenericGameControllerStage getInitialStage() {
		return GenericGameControllerStage.START;
	}
	
	@Override
	protected final void handleStage(GenericGameControllerStage stage) throws GameFlowInterruptedException {
		switch (stage) {
			case START:
				this.nextStage();
				this.handleOnce();
				break;
			case END:
				break;
		}
	}
	
	protected abstract void handleOnce() throws GameFlowInterruptedException;

}