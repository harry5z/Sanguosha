package core.server.game.controllers.mechanics;

import java.util.LinkedList;
import java.util.Queue;

import core.event.game.turn.DealStartTurnEvent;
import core.event.game.turn.DealTurnEvent;
import core.event.game.turn.DiscardTurnEvent;
import core.event.game.turn.DrawStartTurnEvent;
import core.event.game.turn.DrawTurnEvent;
import core.event.game.turn.EndTurnEvent;
import core.player.PlayerCompleteServer;
import core.server.GameRoom;
import core.server.game.Game;
import core.server.game.controllers.GameControllerStage;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedStackItem;

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

	private final Game game;
	private PlayerCompleteServer currentPlayer;
	private TurnStage currentStage;
	private Queue<DelayedStackItem> delayedQueue;
	
	public TurnGameController(GameRoom room) {
		this.game = room.getGame();
		this.currentPlayer = game.findPlayer(player -> player.getPosition() == 0);
		this.currentStage = TurnStage.START_BEGINNING;
		this.delayedQueue = new LinkedList<>();
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
	public void proceed(Game game) throws GameFlowInterruptedException {
		switch (currentStage) {
			case START_BEGINNING:
				this.nextStage();
				return;
			case START:
				this.nextStage();
				return;
			case DELAYED_ARBITRATION_BEGINNING:
				this.delayedQueue = this.currentPlayer.getDelayedQueue();
				if (delayedQueue.isEmpty()) {
					this.currentStage = TurnStage.DRAW_BEGINNING;
				} else {
					this.nextStage();
				}
				return;
			case DELAYED_ARBITRATION:
				if (this.delayedQueue.isEmpty()) {
					this.currentStage = TurnStage.DRAW_BEGINNING;
				} else {
					DelayedStackItem item = this.delayedQueue.poll();
					game.pushGameController(item.type.getController(this.currentPlayer, this));
				}
				return;
			case DRAW_BEGINNING:
				this.nextStage();
				game.emit(new DrawStartTurnEvent(this));
				return;
			case DRAW:
				this.nextStage();
				game.emit(new DrawTurnEvent());
				return;
			case DEAL_BEGINNING:
				this.nextStage();
				game.emit(new DealStartTurnEvent(this));
				return;
			case DEAL:
				this.currentPlayer.clearDisposalArea();
				game.emit(new DealTurnEvent());
				throw new GameFlowInterruptedException();
			case DISCARD_BEGINNING:
				// nothing here yet
				this.nextStage();
				return;
			case DISCARD:
				// nothing here yet
				this.nextStage();
				this.currentPlayer.clearDisposalArea();
				game.emit(new DiscardTurnEvent());
				return;
			case DISCARD_END:
				// nothing here yet
				this.nextStage();
				return;
			case TURN_END:
				this.nextStage();
				this.currentPlayer.clearDisposalArea();
				game.emit(new EndTurnEvent());
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

}
