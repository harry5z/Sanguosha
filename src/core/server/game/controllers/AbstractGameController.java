package core.server.game.controllers;

import core.server.game.Game;

public abstract class AbstractGameController implements GameController {
	
	protected final Game game;
	
	public AbstractGameController(Game game) {
		this.game = game;
	}
	
	protected void onCompleted() {
		this.game.popGameController();
		this.game.getGameController().proceed();
	}

}
