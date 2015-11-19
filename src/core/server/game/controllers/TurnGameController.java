package core.server.game.controllers;

import java.util.stream.Collectors;

import commands.game.client.DealStartGameClientCommmand;
import commands.game.client.DiscardGameClientCommand;
import core.TurnStage;
import core.server.Game;
import net.server.GameRoom;
import player.PlayerComplete;

public class TurnGameController implements GameController {

	private final GameRoom room;
	private final Game game;
	private PlayerComplete currentPlayer;
	private TurnStage currentStage;
	
	public TurnGameController(GameRoom room) {
		this.room = room;
		this.game = room.getGame();
		this.currentPlayer = game.findPlayer(player -> player.getPosition() == 0);
		this.currentStage = TurnStage.START_BEGINNING;
	}
	
	public void nextStage() {
		currentStage = currentStage.nextStage();
		if (currentStage == TurnStage.DEAL_BEGINNING) {
			currentPlayer = game.getNextPlayerAlive(currentPlayer);
		}
	}
	
	public PlayerComplete getCurrentPlayer() {
		return currentPlayer;
	}
	
	@Override
	public void proceed() {
		for (PlayerComplete player : game.getPlayers()) {
			if (player.makeAction(this)) {
				return;
			}
		}
		switch (currentStage) {
			case START_BEGINNING:
				currentStage = currentStage.nextStage();
				proceed();
				return;
			case START:
				currentStage = currentStage.nextStage();
				proceed();
				return;
			case DECISION_BEGINNING:
				currentStage = currentStage.nextStage();
				proceed();
				return;
			case DECISION:
				currentStage = currentStage.nextStage();
				proceed();
				return;
			case DRAW:
				game.drawCards(currentPlayer, 2);
				currentStage = currentStage.nextStage();
				proceed();
				return;
			case DEAL_BEGINNING:
				currentStage = currentStage.nextStage();
				proceed();
				return;
			case DEAL:
				room.sendCommandToPlayers(
					game.getPlayersInfo().stream().collect(
						Collectors.toMap(
							info -> info.getName(), 
							info -> new DealStartGameClientCommmand(currentPlayer.getPlayerInfo())
						)
					)
				);
				return;
			case DISCARD_BEGINNING:
				currentStage = currentStage.nextStage();
				proceed();
				return;
			case DISCARD:
				int amount = currentPlayer.getHandCount() - currentPlayer.getCardOnHandLimit();
				if (amount > 0) {
					room.sendCommandToPlayers(
						game.getPlayersInfo().stream().collect(
							Collectors.toMap(
								info -> info.getName(), 
								info -> new DiscardGameClientCommand(currentPlayer.getPlayerInfo(), amount)
							)
						)
					);
				} else {
					for (PlayerComplete player : game.getPlayers()) {
						player.clearDisposalArea();
					}
					currentStage = currentStage.nextStage();
					proceed();
				}
				return;
			case DISCARD_END:
				currentStage = currentStage.nextStage();
				proceed();
				return;
			case END:
				currentStage = currentStage.nextStage();
				currentPlayer = game.getNextPlayerAlive(currentPlayer);
				proceed();
				return;
			default:
				break;
		}
		
	}
	
	public void setCurrentStage(TurnStage stage) {
		this.currentStage = stage;
	}
	
	public void setCurrentPlayer(PlayerComplete player) {
		this.currentPlayer = player;
	}

}
