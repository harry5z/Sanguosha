package core.server.game.controllers;

import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractGameController<T extends GameControllerStage<?>> implements GameController {
	
	private T currentStage;
	
	public AbstractGameController() {
		this.currentStage = this.getInitialStage();
	}
	
	@Override
	public final void proceed(Game game) throws GameFlowInterruptedException {
		if (this.currentStage.isLastStage()) {
			game.popGameController();
			return;
		}
		this.handleStage(game, this.currentStage);
	}
	
	@SuppressWarnings("unchecked")
	protected final void nextStage() {
		this.currentStage = (T) this.currentStage.nextStage();
	}
	
	public final void setStage(T stage) {
		this.currentStage = stage;
	}
	
	protected abstract void handleStage(Game game, T stage) throws GameFlowInterruptedException;
	
	protected abstract T getInitialStage();
	
}
