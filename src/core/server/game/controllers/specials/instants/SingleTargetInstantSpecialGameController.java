package core.server.game.controllers.specials.instants;

import core.event.game.basic.RequestNeutralizationEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class SingleTargetInstantSpecialGameController extends AbstractInstantSpecialGameController {
	
	protected PlayerCompleteServer target;

	public SingleTargetInstantSpecialGameController(PlayerCompleteServer source, PlayerCompleteServer target) {
		super(source);
		this.target = target;
	}

	@Override
	protected void handleStage(Game game, SpecialStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case TARGET_LOCKED:
				// nothing here yet
				this.nextStage();
				break;
			case NEUTRALIZATION:
				if (this.neutralizedCount >= game.getNumberOfPlayersAlive()) {
					this.neutralizedCount = 0;
					this.nextStage();
				} else {
					if (this.neutralizedCount == 0) {
						game.emit(new RequestNeutralizationEvent(
							this.target.getPlayerInfo(),
							this.getNeutralizationMessage()
						));
					}
					throw new GameFlowInterruptedException();
				}
				break;
			case EFFECT:
				if (!this.neutralized) {
					this.takeEffect(game);
				} else {
					this.nextStage();
				}
				break;
			case TARGET_SWITCH:
				this.nextStage();
				break;
			case END:
				break;
		}
	}
	
	protected abstract void takeEffect(Game game) throws GameFlowInterruptedException;

}
