package core.server.game.controllers;

import java.util.stream.Collectors;

import cards.Card;
import cards.basics.Peach;
import cards.basics.Wine;
import cards.equipments.Equipment;
import commands.game.client.DealStartGameUIClientCommmand;
import commands.game.client.DiscardGameUIClientCommand;
import core.TurnStage;
import core.server.Game;
import exceptions.server.game.InvalidPlayerCommandException;
import net.server.GameRoom;
import player.PlayerComplete;
import player.PlayerCompleteServer;

public class TurnGameController implements 
	GameController, 
	WineUsableGameController, 
	PeachUsableGameController,
	EquipmentUsableGameController {

	private final GameRoom room;
	private final Game game;
	private PlayerCompleteServer currentPlayer;
	private TurnStage currentStage;
	
	public TurnGameController(GameRoom room) {
		this.room = room;
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
	
	public PlayerComplete getCurrentPlayer() {
		return currentPlayer;
	}
	
	@Override
	public void proceed() {
		for (PlayerCompleteServer player : game.getPlayers()) {
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
				for (PlayerComplete player : game.getPlayers()) {
					player.clearDisposalArea();
				}
				room.sendCommandToPlayers(
					game.getPlayersInfo().stream().collect(
						Collectors.toMap(
							info -> info.getName(), 
							info -> new DealStartGameUIClientCommmand(currentPlayer.getPlayerInfo())
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
								info -> new DiscardGameUIClientCommand(currentPlayer.getPlayerInfo(), amount)
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
				while (currentPlayer.isFlipped()) {
					currentPlayer.flip();
					currentPlayer = game.getNextPlayerAlive(currentPlayer);
				}
				proceed();
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
	
	@Override
	public void onEquipped(Equipment equipment) {
		try {
			currentPlayer.removeCardFromHand(equipment);
			currentPlayer.equip(equipment);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
		proceed();
	}

}
