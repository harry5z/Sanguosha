package core.server.game.controllers.specials.delayed;

import cards.Card;
import core.event.game.basic.RequestNeutralizationEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.ArbitrationRequiredGameController;
import core.server.game.controllers.GameControllerStage;
import core.server.game.controllers.mechanics.ArbitrationController;
import core.server.game.controllers.mechanics.TurnGameController;
import core.server.game.controllers.specials.AbstractSpecialGameController;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractDelayedArbitrationController
	extends AbstractSpecialGameController<AbstractDelayedArbitrationController.DelayedArbitrationStage>
	implements ArbitrationRequiredGameController {
	
	public static enum DelayedArbitrationStage implements GameControllerStage<DelayedArbitrationStage> {
		NEUTRALIZATION,
		ARBITRATION,
		EFFECT,
		BEFORE_END,
		END;
	}
	
	protected PlayerCompleteServer target;
	protected final TurnGameController currentTurn;
	protected boolean effective;

	public AbstractDelayedArbitrationController(Game game, PlayerCompleteServer target, TurnGameController turn) {
		super(game);
		this.target = target;
		this.currentTurn = turn;
		this.effective = false;
	}

	@Override
	protected void handleStage(DelayedArbitrationStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case NEUTRALIZATION:
				if (this.neutralizedCount >= this.game.getNumberOfPlayersAlive()) {
					this.neutralizedCount = 0;
					this.nextStage();
				} else {
					if (this.neutralizedCount == 0) {
						this.game.emit(new RequestNeutralizationEvent(
							this.target.getPlayerInfo(),
							this.getNeutralizationMessage()
						));
					}
					throw new GameFlowInterruptedException();
				}
				break;
			case ARBITRATION:
				if (!this.neutralized) {
					this.nextStage();
					this.game.pushGameController(new ArbitrationController(this.game, this, this.target));
				} else {
					this.currentStage = DelayedArbitrationStage.BEFORE_END;
				}
				break;
			case EFFECT:
				this.nextStage();
				this.handleEffect();
				break;
			case BEFORE_END:
				this.nextStage();
				this.beforeEnd();
			case END:
				break;
		}
	}
	
	@Override
	protected final DelayedArbitrationStage getInitialStage() {
		return DelayedArbitrationStage.NEUTRALIZATION;
	}
	
	@Override
	public void onArbitrationCompleted(Card card) {
		this.effective = this.isArbitrationEffective(card);
	}
	
	protected abstract void handleEffect();
	
	protected abstract void beforeEnd();
	
	protected abstract boolean isArbitrationEffective(Card card);
	
}
