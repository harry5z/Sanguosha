package commands.game.client;

import java.util.HashSet;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import core.server.game.controllers.mechanics.HealGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedStackItem;

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
				
				try {
					Set<Card> disposedCards = new HashSet<>();
					// Dead player discards all cards on hand
					// TODO move to GameController
					dyingPlayer.discardCards(dyingPlayer.getCardsOnHand());
					
					// Dead player discards all equipments
					for (Equipment equipment : dyingPlayer.getEquipments()) {
						dyingPlayer.unequip(equipment.getEquipmentType());
						disposedCards.add(equipment);
					}
					
					// Dead player discards all delayed items
					for (DelayedStackItem item : dyingPlayer.getDelayedQueue()) {
						dyingPlayer.removeDelayed(item.type);
						disposedCards.add(item.delayed);
					}
					
					dyingPlayer.kill();
					game.pushGameController(new RecycleCardsGameController(dyingPlayer, disposedCards));
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
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
