package core.server.game.controllers.specials.delayed;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.ArbitrationRequiredGameController;
import core.server.game.controllers.GameControllerStage;
import core.server.game.controllers.mechanics.ArbitrationController;
import core.server.game.controllers.specials.AbstractSpecialGameController;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractDelayedArbitrationController
	extends AbstractSpecialGameController<AbstractDelayedArbitrationController.DelayedArbitrationStage>
	implements ArbitrationRequiredGameController {
	
	public static enum DelayedArbitrationStage implements GameControllerStage<DelayedArbitrationStage> {
		NULLIFICATION,
		ARBITRATION,
		EFFECT,
		BEFORE_END,
		END;
	}
	
	protected PlayerCompleteServer target;
	protected boolean effective;

	public AbstractDelayedArbitrationController(PlayerCompleteServer target) {
		this.target = target;
		this.effective = false;
	}

	@Override
	protected void handleStage(GameInternal game, DelayedArbitrationStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case NULLIFICATION:
				handleNullificationStage(game);
				break;
			case ARBITRATION:
				if (!this.nullified) {
					this.nextStage();
					game.pushGameController(new ArbitrationController(this, this.target));
				} else {
					this.setStage(DelayedArbitrationStage.BEFORE_END);
				}
				break;
			case EFFECT:
				this.nextStage();
				if (this.effective) {
					this.handleEffect(game);
					game.log(getLogOnEffectiveArbitration());
				} else {
					game.log(getLogOnIneffectiveArbitration());
				}
				break;
			case BEFORE_END:
				this.nextStage();
				this.beforeEnd(game);
			case END:
				break;
		}
	}
	
	@Override
	protected final DelayedArbitrationStage getInitialStage() {
		return DelayedArbitrationStage.NULLIFICATION;
	}
	
	@Override
	public void onArbitrationCompleted(Card card) {
		this.effective = this.isArbitrationEffective(card);
	}
	
	protected abstract void handleEffect(GameInternal game);
	
	protected abstract void beforeEnd(GameInternal game);
	
	protected abstract boolean isArbitrationEffective(Card card);
	
	protected abstract BattleLog getLogOnEffectiveArbitration();
	
	protected abstract BattleLog getLogOnIneffectiveArbitration();
}
