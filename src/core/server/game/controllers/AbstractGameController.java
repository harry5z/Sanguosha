package core.server.game.controllers;

import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractGameController<T extends GameControllerStage<?>> implements GameController {
	
	protected final Game game;
	protected T currentStage;
	
	public AbstractGameController(Game game) {
		this.game = game;
		this.currentStage = this.getInitialStage();
	}
	
	protected final void onUnloaded() {
		this.game.popGameController();
	}
	
	@Override
	public final void proceed() throws GameFlowInterruptedException {
		if (this.currentStage.isLastStage()) {
			this.game.popGameController();
			return;
		}
		this.handleStage(this.currentStage);
	}
	
	@SuppressWarnings("unchecked")
	protected void nextStage() {
		this.currentStage = (T) this.currentStage.nextStage();
	}
	
	protected abstract void handleStage(T stage) throws GameFlowInterruptedException;
	
	protected abstract T getInitialStage();
	
}
