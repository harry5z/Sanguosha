package commands.game.client;

import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import core.server.game.controllers.mechanics.HealGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DeathResolutionGameController
	extends AbstractGameController<DeathResolutionGameController.DeathResolutionStage> {
	
	public static enum DeathResolutionStage implements GameControllerStage<DeathResolutionStage> {
		RESCUE_INQUIRY,
		DEATH,
		AFTER_DEATH_SETTLEMENT,
		END;
	}
	
	private final PlayerCompleteServer dyingPlayer;
	private PlayerCompleteServer currentRescuer;
	private boolean roundEnded;
	
	public DeathResolutionGameController(PlayerCompleteServer dyingPlayer, PlayerCompleteServer currentPlayer) {
		this.dyingPlayer = dyingPlayer;
		this.currentRescuer = currentPlayer;
		this.roundEnded = false;
	}

	@Override
	protected void handleStage(GameInternal game, DeathResolutionStage stage) throws GameFlowInterruptedException {
		switch (stage) {
			case RESCUE_INQUIRY:
				if (!dyingPlayer.isDying()) {
					// if player is no longer dying, end
					this.setStage(DeathResolutionStage.END);
					return;
				}
				if (roundEnded) {
					// round finished but player is still dying, so kill the player
					this.nextStage();
					return;
				}
				throw new GameFlowInterruptedException(new RequestRescueGameClientCommand(currentRescuer, dyingPlayer));
			case DEATH:
				this.nextStage();
				dyingPlayer.kill();
				// TODO resolve game end
				break;
			case AFTER_DEATH_SETTLEMENT:
				this.nextStage();
				// TODO resolve after death settlement
				break;
			case END:
				break;
		}
	}

	@Override
	protected DeathResolutionStage getInitialStage() {
		return DeathResolutionStage.RESCUE_INQUIRY;
	}
	
	public void onRescueReaction(GameInternal game, boolean rescued) {
		if (rescued) {
			game.pushGameController(new HealGameController(currentRescuer, dyingPlayer));
		} else {
			// ask the next player if they would rescue
			currentRescuer = game.getNextPlayerAlive(currentRescuer);
			if (currentRescuer == game.getCurrentPlayer()) {
				roundEnded = true;
			}
		}
	}
	
	public PlayerCompleteServer getCurrentRescuer() {
		return currentRescuer;
	}
	
	public PlayerCompleteServer getDyingPlayer() {
		return dyingPlayer;
	}

}
