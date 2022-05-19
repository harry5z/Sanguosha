package core.server.game.controllers.mechanics;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import core.event.game.turn.DealStartTurnEvent;
import core.event.game.turn.DrawStartTurnEvent;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedStackItem;
import utils.DelayedType;

public class TurnGameController implements GameController {
	
	public static enum TurnStage implements GameControllerStage<TurnStage> {
		START_BEGINNING,
		START,
		DELAYED_ARBITRATION_BEGINNING,
		DELAYED_ARBITRATION,
		DRAW_BEGINNING,
		DRAW,
		DEAL_BEGINNING,
		DEAL,
		DISCARD_BEGINNING,
		DISCARD,
		DISCARD_END,
		TURN_END;
	}

	private final GameInternal game;
	private PlayerCompleteServer currentPlayer;
	private TurnStage currentStage;
	private final Set<TurnStage> skippedStages;
	private final Set<DelayedType> seenDelayedTypes;
	
	public TurnGameController(GameInternal game) {
		this.game = game;
		this.currentPlayer = game.findPlayer(player -> player.getPosition() == 0);
		this.currentStage = TurnStage.START_BEGINNING;
		this.skippedStages = new HashSet<>();
		this.seenDelayedTypes = new HashSet<>();
	}
	
	public void nextStage() {
		switch (currentStage) {
			case TURN_END:
				currentPlayer = game.getNextPlayerAlive(currentPlayer);
				while (currentPlayer.isFlipped()) {
					currentPlayer.flip();
					currentPlayer = game.getNextPlayerAlive(currentPlayer);
				}
				break;
			case DEAL:
				try {
					currentPlayer.setWineUsed(0);
					currentPlayer.resetWineEffective();
					currentPlayer.setAttackUsed(0);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
		currentStage = currentStage.nextStage();
	}
	
	public PlayerCompleteServer getCurrentPlayer() {
		return currentPlayer;
	}
	
	@Override
	public void proceed(GameInternal game) throws GameFlowInterruptedException {
		if (skippedStages.contains(currentStage)) {
			skippedStages.remove(currentStage);
			nextStage();
			return;
		}
		switch (currentStage) {
			case START_BEGINNING:
				this.nextStage();
				return;
			case START:
				this.nextStage();
				return;
			case DELAYED_ARBITRATION_BEGINNING:
				if (this.currentPlayer.getDelayedQueue().isEmpty()) {
					this.currentStage = TurnStage.DRAW_BEGINNING;
				} else {
					this.nextStage();
				}
				return;
			case DELAYED_ARBITRATION:
				if (this.currentPlayer.getDelayedQueue().isEmpty() ||
					this.seenDelayedTypes.containsAll(this.currentPlayer.getDelayedQueue().stream().map(item -> item.type).collect(Collectors.toSet()))
				) {
					this.currentStage = TurnStage.DRAW_BEGINNING;
				} else {
					DelayedStackItem item = this.currentPlayer.getDelayedQueue().poll();
					// do not initiate arbitration for the same delayed type multiple times
					// in particular, Lightning
					if (this.seenDelayedTypes.contains(item.type)) {
						return;
					}
					this.seenDelayedTypes.add(item.type);
					game.pushGameController(item.type.getController(this.currentPlayer));
				}
				return;
			case DRAW_BEGINNING:
				this.nextStage();
				game.emit(new DrawStartTurnEvent(this));
				return;
			case DRAW:
				this.nextStage();
				game.pushGameController(new ReceiveCardsGameController(currentPlayer, game.getDeck().drawMany(2)));
				return;
			case DEAL_BEGINNING:
				this.nextStage();
				game.emit(new DealStartTurnEvent(this));
				return;
			case DEAL:
				// Does not call nextStage because this may be called multiple times
				game.pushGameController(new DealPhaseGameController());
				return;
			case DISCARD_BEGINNING:
				// nothing here yet
				this.nextStage();
				return;
			case DISCARD:
				// nothing here yet
				this.nextStage();
				game.pushGameController(new DiscardPhaseGameController());
				return;
			case DISCARD_END:
				// nothing here yet
				this.nextStage();
				return;
			case TURN_END:
				this.nextStage();
				this.seenDelayedTypes.clear();
				this.currentPlayer.resetPlayerStates();
				this.currentPlayer.clearDisposalArea();
				return;
			default:
				break;
		}
		
	}
	
	public void setCurrentStage(TurnStage stage) {
		this.currentStage = stage;
	}
	
	public void setCurrentPlayer(PlayerCompleteServer player) {
		this.currentPlayer = player;
	}
	
	public void skipStage(TurnStage stage) {
		this.skippedStages.add(stage);
	}

}
