package core.server.game.controllers.specials.delayed;

import cards.Card;
import core.event.game.basic.RequestNeutralizationEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.ArbitrationController;
import core.server.game.controllers.TurnGameController;
import core.server.game.controllers.interfaces.ArbitrationRequiredGameController;
import core.server.game.controllers.specials.AbstractSpecialGameController;
import exceptions.server.game.GameFlowInterruptedException;
import utils.EnumWithNextStage;

public abstract class AbstractDelayedArbitrationController extends AbstractSpecialGameController implements ArbitrationRequiredGameController {
	
	public static enum DelayedArbitrationStage implements EnumWithNextStage<DelayedArbitrationStage> {
		NEUTRALIZATION,
		ARBITRATION,
		EFFECT,
		END;
	}
	
	protected DelayedArbitrationStage stage;
	protected PlayerCompleteServer target;
	protected final TurnGameController currentTurn;
	protected boolean effective;

	public AbstractDelayedArbitrationController(Game game, PlayerCompleteServer target, TurnGameController turn) {
		super(game);
		this.stage = DelayedArbitrationStage.NEUTRALIZATION;
		this.target = target;
		this.currentTurn = turn;
		this.effective = false;
	}

	@Override
	public void proceed() {
		switch(this.stage) {
			case NEUTRALIZATION:
				// WARNING: may need another initiator if some player died during neutralization check
				if (this.neutralizedCount >= this.game.getNumberOfPlayersAlive()) {
					this.neutralizedCount = 0;
					this.stage = this.stage.nextStage();
					this.proceed();
				} else if (this.neutralizedCount == 0) {
					try {
						this.game.emit(new RequestNeutralizationEvent(this.target.getPlayerInfo()));
					} catch (GameFlowInterruptedException e) {
						e.resume();
					}
				}
				break;
			case ARBITRATION:
				if (!this.neutralized) {
					this.stage = this.stage.nextStage();
					this.game.pushGameController(new ArbitrationController(this.game, this.target));
				} else {
					this.stage = DelayedArbitrationStage.END;
				}
				this.game.getGameController().proceed();
				break;
			case EFFECT:
				if (this.effective) {
					this.takeEffect();
				}
				this.stage = this.stage.nextStage();
				this.game.getGameController().proceed();
				break;
			case END:
				this.beforeEnd();
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}
	}
	
	@Override
	public void onArbitrationCompleted(Card card) {
		this.effective = this.isArbitrationEffective(card);
	}
	
	protected abstract void takeEffect();
	
	protected abstract void beforeEnd();
	
	protected abstract boolean isArbitrationEffective(Card card);
	
}
