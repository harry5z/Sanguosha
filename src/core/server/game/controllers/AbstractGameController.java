package core.server.game.controllers;

import core.server.game.Game;

public abstract class AbstractGameController<T extends GameControllerStage<?>> implements GameController {
	
	protected final Game game;
	protected T stage;
	
	public AbstractGameController(Game game) {
		this.game = game;
		this.stage = this.getInitialStage();
	}
	
	protected final void onUnloaded() {
		this.game.popGameController();
	}
	
	protected abstract T getInitialStage();
	
}
