package core.server.game.controllers.specials.instants;

import commands.game.client.RequestNullificationGameUIClientCommand;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class SingleTargetInstantSpecialGameController extends AbstractInstantSpecialGameController {
	
	protected PlayerCompleteServer target;

	public SingleTargetInstantSpecialGameController(PlayerCompleteServer source, PlayerCompleteServer target) {
		super(source);
		this.target = target;
	}

	@Override
	protected void handleStage(GameInternal game, SpecialStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case LOADED:
				// nothing here yet
				this.nextStage();
				break;
			case TARGET_LOCKED:
				// nothing here yet
				this.nextStage();
				break;
			case NULLIFICATION:
				if (this.nullifiedCount >= game.getNumberOfPlayersAlive()) {
					this.nullifiedCount = 0;
					this.nextStage();
				} else {
					if (this.nullifiedCount == 0) {
						game.getConnectionController().sendCommandToAllPlayers(
							new RequestNullificationGameUIClientCommand(getNullificationMessage())
						);
					}
					throw new GameFlowInterruptedException();
				}
				break;
			case EFFECT:
				if (!this.nullified) {
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
	
	protected abstract void takeEffect(GameInternal game) throws GameFlowInterruptedException;

}
