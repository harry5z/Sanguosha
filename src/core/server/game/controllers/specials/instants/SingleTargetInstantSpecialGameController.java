package core.server.game.controllers.specials.instants;

import core.event.game.basic.RequestNeutralizationEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class SingleTargetInstantSpecialGameController extends AbstractInstantSpecialGameController {
	
	protected PlayerCompleteServer target;

	public SingleTargetInstantSpecialGameController(PlayerInfo source, Game game) {
		this(source, source, game);
	}
	
	public SingleTargetInstantSpecialGameController(PlayerInfo source, PlayerInfo target, Game game) {
		super(source, game);
		this.target = game.findPlayer(target);
	}

	@Override
	public final void proceed() {
		// TODO: server side sanity check
		switch(this.stage) {
			case TARGET_LOCKED:
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case NEUTRALIZATION:
				// WARNING: may need another initiator if some player died during neutralization check
				if (this.neutralizedCount >= this.game.getNumberOfPlayersAlive()) {
					this.neutralizedCount = 0;
					this.stage = this.stage.nextStage();
					this.proceed();
				} else if (this.neutralizedCount == 0) {
					try {
						this.game.emit(new RequestNeutralizationEvent(
							this.target.getPlayerInfo(),
							this.getNeutralizationMessage()
						));
					} catch (GameFlowInterruptedException e) {
						e.resume();
					}
				}
				break;
			case EFFECT:
				if (!this.neutralized) {
					this.takeEffect();
				} else {
					this.stage = this.stage.nextStage();
					this.proceed();
				}
				break;
			case EFFECT_TAKEN:
				this.game.popGameController();
				this.game.getGameController().proceed();
				break;
		}
	}
	
	protected abstract void takeEffect();

}
