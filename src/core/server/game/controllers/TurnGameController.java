package core.server.game.controllers;

import cards.Card;
import cards.basics.Peach;
import cards.basics.Wine;
import core.TurnStage;
import core.event.game.turn.DealTurnEvent;
import core.event.game.turn.DiscardTurnEvent;
import core.event.game.turn.DrawTurnEvent;
import core.player.PlayerCompleteServer;
import core.server.GameRoom;
import core.server.game.Game;
import core.server.game.controllers.interfaces.PeachUsableGameController;
import core.server.game.controllers.interfaces.WineUsableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class TurnGameController implements 
	GameController, 
	WineUsableGameController, 
	PeachUsableGameController {

	private final Game game;
	private PlayerCompleteServer currentPlayer;
	private TurnStage currentStage;
	
	public TurnGameController(GameRoom room) {
		this.game = room.getGame();
		this.currentPlayer = game.findPlayer(player -> player.getPosition() == 0);
		this.currentStage = TurnStage.START_BEGINNING;
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
			case JUDGMENT_BEGINNING:
				this.nextStage();
				return;
			case JUDGMENT:
				this.nextStage();
				return;
			case DRAW:
				try {
					this.game.emit(new DrawTurnEvent());
				} catch (GameFlowInterruptedException e1) {
					// Do no proceed automatically
					return;
				}
				this.nextStage();
				return;
			case DEAL_BEGINNING:
				this.nextStage();
				return;
			case DEAL:
				try {
					this.game.emit(new DealTurnEvent());
				} catch (GameFlowInterruptedException e) {
					// Do nothing
				}
				return;
			case DISCARD_BEGINNING:
				this.nextStage();
				return;
			case DISCARD:
				try {
					this.game.emit(new DiscardTurnEvent());
				} catch (GameFlowInterruptedException e) {
					// Do no proceed automatically
					return;
				}
				this.nextStage();
				return;
			case DISCARD_END:
				this.nextStage();
				return;
			case END:
				this.nextStage();
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
	
	@Override
	public void onWineUsed(Card card) {
		try {
			if (card != null) { 
				if (!(card instanceof Wine)) {
					throw new InvalidPlayerCommandException("card " + card + " is not wine");
				}
				if (!currentPlayer.getCardsOnHand().contains(card)) {
					throw new InvalidPlayerCommandException("card " + card + " is not on current player's hand");
				}
			}
			if (currentPlayer.isWineUsed()) {
				throw new InvalidPlayerCommandException("wine is already used");
			}
			currentPlayer.useCard(card);
			currentPlayer.useWine();
			proceed();
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
		proceed();
	}
	
	@Override
	public void onPeachUsed(Card card) {
		try {
			if (card != null) { 
				if (!(card instanceof Peach)) {
					throw new InvalidPlayerCommandException("card " + card + " is not peach");
				}
				if (!currentPlayer.getCardsOnHand().contains(card)) {
					throw new InvalidPlayerCommandException("card " + card + " is not on current player's hand");
				}
			}
			if (currentPlayer.getHealthCurrent() == currentPlayer.getHealthLimit()) {
				throw new InvalidPlayerCommandException("player health is maximum");
			}
			currentPlayer.useCard(card);
			currentPlayer.changeHealthCurrentBy(1);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
		proceed();
	}
	
}
