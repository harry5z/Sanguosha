package core.server.game.controllers;

import core.server.game.Game;

public abstract class AbstractGameController implements GameController {
	
	protected final Game game;
	private GameController next;
	
	public AbstractGameController(Game game) {
		this.game = game;
		this.next = null;
	}
	
	protected void onCompleted() {
		this.game.popGameController();
		if (this.next != null) {
			this.game.pushGameController(this.next);
		}
		this.game.getGameController().proceed();
	}
	
	public AbstractGameController setNextController(GameController next) {
		this.next = next;
		return this;
	}
	
	public void removeNextController() {
		this.next = null;
	}

}
