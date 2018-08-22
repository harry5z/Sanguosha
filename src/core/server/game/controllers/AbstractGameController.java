package core.server.game.controllers;

import core.server.game.Game;

public abstract class AbstractGameController implements GameController {
	
	protected final Game game;
	private AbstractGameController next;
	
	public AbstractGameController(Game game) {
		this.game = game;
		this.next = null;
	}
	
	protected final void onUnloaded() {
		this.game.popGameController();
		if (this.next != null) {
			this.game.pushGameController(this.next);
			this.onNextControllerLoaded(this.next);
		} else {
			this.onNextControllerLoaded(this.game.getGameController());
		}
	}
	
	public AbstractGameController setNextController(AbstractGameController next) {
		this.next = next;
		return this;
	}
	
	public void removeNextController() {
		this.next = null;
	}
	
	protected void onNextControllerLoaded(GameController controller) {}

}
