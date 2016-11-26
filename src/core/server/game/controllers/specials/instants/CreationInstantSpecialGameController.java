package core.server.game.controllers.specials.instants;

import java.util.stream.Collectors;

import commands.game.client.RequestNeutralizationGameUIClientCommand;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Game;

public class CreationInstantSpecialGameController implements InstantSpecialGameController {

	private SpecialStage stage;
	private PlayerCompleteServer source;
	private final Game game;
	private final GameRoom room;
	private boolean neutralized;
	private int neutralizedCount;

	public CreationInstantSpecialGameController(PlayerInfo source, GameRoom room) {
		this.stage = SpecialStage.TARGET_LOCKED;
		this.room = room;
		this.game = room.getGame();
		this.source = game.findPlayer(source);
		this.neutralized = false;
		this.neutralizedCount = 0;
	}
	
	@Override
	public void proceed() {
		switch(stage) {
			case TARGET_LOCKED:
				stage = stage.nextStage();
				proceed();
				break;
			case NEUTRALIZATION:
				room.sendCommandToPlayers(
					game.getPlayersInfo().stream().collect(
						Collectors.toMap(
							info -> info.getName(),
							info -> new RequestNeutralizationGameUIClientCommand()
						)
					)
				);
				break;
			case EFFECT:
				if (neutralized) {
					stage = stage.nextStage();
					proceed();
				} else {
					game.drawCards(source, 2);
				}
				break;
			case EFFECT_TAKEN:
				source.clearDisposalArea();
				game.popGameController();
				game.getGameController().proceed();
				break;
		}
	}

	@Override
	public void onNeutralized() {
		this.neutralized = !this.neutralized;
		this.neutralizedCount = 0;
		room.sendCommandToPlayers(
			game.getPlayersInfo().stream().collect(
				Collectors.toMap(
					info -> info.getName(),
					info -> new RequestNeutralizationGameUIClientCommand()
				)
			)
		);
	}
	
	@Override
	public void onNeutralizationCanceled() {
		neutralizedCount++;
		if (neutralizedCount == game.getNumberOfPlayersAlive()) {
			neutralizedCount = 0;
			stage = stage.nextStage();
			proceed();
		}
	}
}
