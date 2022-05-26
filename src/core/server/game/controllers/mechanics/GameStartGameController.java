package core.server.game.controllers.mechanics;

import java.util.Collections;
import java.util.List;

import core.heroes.Hero;
import core.heroes.original.GanNing;
import core.heroes.original.SunQuan;
import core.player.PlayerCompleteServer;
import core.player.Role;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public class GameStartGameController extends AbstractGameController<GameStartGameController.GameStartStage> {
	
	public static enum GameStartStage implements GameControllerStage<GameStartStage> {
		ROLE_ASSIGNMENT,
		EMPEROR_HERO_SELECTION,
		OTHERS_HERO_SELECTION,
		INITIAL_DRAW,
		END;
	}
	
	@Override
	protected void handleStage(GameInternal game, GameStartStage stage) throws GameFlowInterruptedException {
		;
		switch (stage) {
			case ROLE_ASSIGNMENT:
				this.nextStage();
				List<PlayerCompleteServer> players = game.getPlayersAlive();
				Collections.shuffle(players);
				players.forEach(player -> player.setRole(Role.ROLES_LIST.get(player.getPosition())));
				// Emperor always starts first
				game.getTurnController().setCurrentPlayer(game.findPlayer(p -> p.getRole() == Role.EMPEROR));
				break;
			case EMPEROR_HERO_SELECTION:
				this.nextStage();
				PlayerCompleteServer emperor = game.findPlayer(p -> p.getRole() == Role.EMPEROR);
				Hero emperorHero = new SunQuan(); // TODO replace with player selected hero
				emperor.setHero(emperorHero);
				// by default, a player's health limit is equal to their hero's health limit
				emperor.setHealthLimit(emperorHero.getHealthLimit());
				if (game.getNumberOfPlayersAlive() > 2) {
					// In a game with more than 2 players, the Emperor starts with 1 extra health limit
					emperor.changeHealthLimitBy(1);
				}
				emperor.setHealthCurrent(emperor.getHealthLimit());
				emperor.onGameReady(game);
				break;
			case OTHERS_HERO_SELECTION:
				this.nextStage();
				game.getPlayersAlive().forEach(player -> {
					if (player.getRole() == Role.EMPEROR) {
						return;
					}
					Hero playerHero = new GanNing(); // TODO replace with player selected hero
					player.setHero(playerHero);
					// by default, a player's health limit is equal to their hero's health limit
					player.setHealthLimit(playerHero.getHealthLimit());
					player.setHealthCurrent(player.getHealthLimit());
					player.onGameReady(game);
				});
				break;
			case INITIAL_DRAW:
				this.nextStage();
				for (PlayerCompleteServer player : game.getPlayersAlive()) {
					player.addCards(game.getDeck().drawMany(4));
				}
				break;
			case END:
				break;
		}
	}

	@Override
	protected GameStartStage getInitialStage() {
		return GameStartStage.ROLE_ASSIGNMENT;
	}

}
