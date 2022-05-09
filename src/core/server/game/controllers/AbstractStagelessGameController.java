package core.server.game.controllers;

import core.server.game.Game;

public abstract class AbstractStagelessGameController extends AbstractGameController<AbstractStagelessGameController.GenericGameControllerStage> {

	/**
	 * Used for GameControllers which have no Stage
	 */
	public static enum GenericGameControllerStage implements GameControllerStage<GenericGameControllerStage> {
		END;
	}
	
	public AbstractStagelessGameController(Game game) {
		super(game);
	}
	
	@Override
	protected final GenericGameControllerStage getInitialStage() {
		return GenericGameControllerStage.END;
	}

}
