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
		END;
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
			case END:
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
		proceed();
	}
	
	public PlayerCompleteServer getCurrentPlayer() {
		return currentPlayer;
	}
	
	@Override
	public void proceed() {
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
					this.proceed();
				} else {
					this.nextStage();
				}
				return;
			case DELAYED_ARBITRATION:
				if (this.delayedQueue.isEmpty()) {
					this.currentStage = TurnStage.DRAW_BEGINNING;
					this.proceed();
				} else {
					DelayedStackItem item = this.delayedQueue.poll();
					this.game.pushGameController(item.type.getController(this.game, this.currentPlayer, this));
					this.game.getGameController().proceed();
				}
				return;
			case DRAW_BEGINNING:
				try {
					this.game.emit(new DrawStartTurnEvent(this));
					this.nextStage();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				return;
			case DRAW:
				try {
					this.game.emit(new DrawTurnEvent());
					this.nextStage();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				return;
			case DEAL_BEGINNING:
				try {
					this.game.emit(new DealStartTurnEvent(this));
					this.nextStage();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				return;
			case DEAL:
				try {
					this.currentPlayer.clearDisposalArea();
					this.game.emit(new DealTurnEvent());
					this.nextStage();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				return;
			case DISCARD_BEGINNING:
				this.nextStage();
				return;
			case DISCARD:
				this.currentPlayer.clearDisposalArea();
				try {
					this.game.emit(new DiscardTurnEvent());
					this.nextStage();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
				return;
			case DISCARD_END:
				this.nextStage();
				return;
			case END:
				try {
					this.game.emit(new EndTurnEvent());
					this.currentPlayer.clearDisposalArea();
					this.nextStage();
				} catch (GameFlowInterruptedException e) {
					e.resume();
				}
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
