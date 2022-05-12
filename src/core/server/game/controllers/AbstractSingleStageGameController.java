package core.server.game.controllers;

import core.server.game.GameInternal;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractSingleStageGameController extends AbstractGameController<AbstractSingleStageGameController.GenericGameControllerStage> {

	/**
	 * Used for GameControllers which have only 1 Stage
	 */
	public static enum GenericGameControllerStage implements GameControllerStage<GenericGameControllerStage> {
		START,
		END;
	}
	
	@Override
	protected final GenericGameControllerStage getInitialStage() {
		return GenericGameControllerStage.START;
	}
	
	@Override
	protected final void handleStage(GameInternal game, GenericGameControllerStage stage) throws GameFlowInterruptedException {
		switch (stage) {
			case START:
				this.nextStage();
				this.handleOnce(game);
				break;
			case END:
				break;
		}
	}
	
	protected abstract void handleOnce(GameInternal game) throws GameFlowInterruptedException;

}
