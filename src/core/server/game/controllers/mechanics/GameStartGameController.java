package core.server.game.controllers.mechanics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import commands.game.client.EmperorHeroSelectionGameClientCommand;
import commands.game.client.NonEmperorHeroSelectionGameClientCommand;
import core.heroes.Hero;
import core.heroes.HeroPool;
import core.player.PlayerCompleteServer;
import core.player.Role;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;

public class GameStartGameController extends AbstractGameController<GameStartGameController.GameStartStage> {
	
	public static enum GameStartStage implements GameControllerStage<GameStartStage> {
		ROLE_ASSIGNMENT,
		EMPEROR_HERO_SELECTION_REQUEST,
		EMPEROR_HERO_SELECTION,
		OTHERS_HERO_SELECTION_REQUEST,
		OTHERS_HERO_SELECTION,
		INITIAL_DRAW,
		END;
	}
	
	private final Map<PlayerCompleteServer, List<Hero>> allowedHeroChoices; // allowed heroes for each player
	private final Map<PlayerCompleteServer, Integer> heroChoices; // selected hero (index) for each player
	
	public GameStartGameController() {
		allowedHeroChoices = new HashMap<>();
		heroChoices = new HashMap<>();
	}
	
	@Override
	protected void handleStage(GameInternal game, GameStartStage stage) throws GameFlowInterruptedException {
		PlayerCompleteServer emperor;
		switch (stage) {
			case ROLE_ASSIGNMENT:
				this.nextStage();
				List<PlayerCompleteServer> players = game.getPlayersAlive();
				Collections.shuffle(players);
				for (int i = 0; i< players.size(); i++) {
					players.get(i).setRole(Role.ROLES_LIST.get(i));
				}
				// Emperor always starts first
				game.getTurnController().setCurrentPlayer(game.findPlayer(p -> p.getRole() == Role.EMPEROR));
				break;
			case EMPEROR_HERO_SELECTION_REQUEST:
				this.nextStage();
				List<Hero> availableHeroes = new ArrayList<>();
				// emperor is always allowed to select emperor heroes
				availableHeroes.addAll(HeroPool.getEmperorHeroes());
				
				List<Hero> otherHeroes = new ArrayList<>(HeroPool.getNonEmperorHeroes());
				Collections.shuffle(otherHeroes);
				// select 3 other heroes for the emperor to choose from;
				availableHeroes.addAll(otherHeroes.subList(0, 3));
				
				emperor = game.findPlayer(p -> p.getRole() == Role.EMPEROR);
				allowedHeroChoices.put(emperor, availableHeroes);
				throw new GameFlowInterruptedException(new EmperorHeroSelectionGameClientCommand(emperor, availableHeroes));
			case EMPEROR_HERO_SELECTION:
				emperor = game.findPlayer(p -> p.getRole() == Role.EMPEROR);
				Hero emperorHero = allowedHeroChoices.get(emperor).get(heroChoices.get(emperor));
				emperor.setHero(emperorHero);
				// by default, a player's health limit is equal to their hero's health limit
				emperor.setHealthLimit(emperorHero.getHealthLimit());
				if (game.getNumberOfPlayersAlive() > 2) {
					// In a game with more than 2 players, the Emperor starts with 1 extra health limit
					emperor.changeHealthLimitBy(1);
				}
				emperor.setHealthCurrent(emperor.getHealthLimit());
				emperor.onGameReady(game);
				allowedHeroChoices.remove(emperor);
				heroChoices.remove(emperor);
				this.nextStage();
				break;
			case OTHERS_HERO_SELECTION_REQUEST:
				this.nextStage();
				for (PlayerCompleteServer player : game.getPlayersAlive()) {
					if (player.getRole() == Role.EMPEROR) {
						continue;
					}
					List<Hero> heroes = new ArrayList<>(HeroPool.getAllHeroes());
					Collections.shuffle(heroes);
					// give each player 3 random heroes to select from
					// TODO increase selection limit and make each player's hero pool mutually exclusive
					// TODO remove emperor's hero from pool too
					allowedHeroChoices.put(player, new ArrayList<>(heroes.subList(0, 3)));
				}
				throw new GameFlowInterruptedException(new NonEmperorHeroSelectionGameClientCommand(Map.copyOf(allowedHeroChoices)));
			case OTHERS_HERO_SELECTION:
				if (heroChoices.size() < allowedHeroChoices.size()) {
					// not everyone has selected yet
					throw new GameFlowInterruptedException();
				}
				heroChoices.forEach((player, index) -> {
					Hero playerHero = allowedHeroChoices.get(player).get(index);
					player.setHero(playerHero);
					// by default, a player's health limit is equal to their hero's health limit
					player.setHealthLimit(playerHero.getHealthLimit());
					player.setHealthCurrent(player.getHealthLimit());
					player.onGameReady(game);
				});
				this.nextStage();
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
	
	public void validateHeroSelection(PlayerCompleteServer source, int index) throws IllegalPlayerActionException {
		if (!allowedHeroChoices.containsKey(source)) {
			throw new IllegalPlayerActionException("Hero Selection: Player '" + source.getName() + "' is not allowed to select a hero");
		}
		if (index < 0 || index > allowedHeroChoices.get(source).size() - 1) {
			throw new IllegalPlayerActionException("Hero Selection: Player '" + source.getName() + "' selection out of range");
		}
	}
	
	public void setHeroSelection(PlayerCompleteServer source, int index) {
		heroChoices.put(source, index);
	}
	
	/**
	 * This should be called by the default response command on timeout
	 */
	public void setDefaultHeroSelection() {
		// for whoever that failed to select a hero in time, simply select a random one
		for (PlayerCompleteServer player : allowedHeroChoices.keySet()) {
			if (!heroChoices.containsKey(player)) {
				heroChoices.put(player, new Random().nextInt(allowedHeroChoices.get(player).size()));
			}
		}
	}

}
