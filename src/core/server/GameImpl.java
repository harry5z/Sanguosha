package core.server;

import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import commands.game.client.EnterGameRoomGameClientCommand;
import core.Deck;
import core.PlayerInfo;
import core.server.game.controllers.GameController;
import core.server.game.controllers.TurnGameController;
import heroes.original.Blank;
import listeners.server.ServerInGameCardOnHandListener;
import listeners.server.ServerInGameEquipmentListener;
import listeners.server.ServerInGameHealthListener;
import net.server.GameConfig;
import net.server.GameRoom;
import player.PlayerComplete;

/**
 * The game framework. Currently only the original game
 * (Emperor-loyalist-rebel-usurper), but in the future may add other kinds (1v1,
 * 3v3, Total War, etc.)
 * 
 * @author Harry
 *
 */
public class GameImpl implements Game {
	private List<PlayerComplete> players;
	private Set<String> playerNames;
	private GameRoom room; // the room where the game is held
	private Deck deck;// deck, currently only original game deck as well
	private GameConfig config;
	private Stack<GameController> controllers;

	public GameImpl(GameRoom room, GameConfig config, List<PlayerInfo> playerInfos) {
		this.room = room;
		this.config = config;
		this.deck = new Deck(config.getDeckPacks());
		this.players = playerInfos.stream()
			.map(info -> new PlayerComplete(info.getName(), info.getPosition()))
			.collect(Collectors.toList());

		this.playerNames = playerInfos.stream().map(info -> info.getName()).collect(Collectors.toSet());
		this.controllers = new Stack<>();
	}

	@Override
	public List<PlayerInfo> getPlayersInfo() {
		return this.players.stream().map(player -> player.getPlayerInfo()).collect(Collectors.toList());
	}

	@Override
	public List<PlayerComplete> getPlayers() {
		return players;
	}

	@Override
	public PlayerComplete findPlayer(PlayerInfo info) {
		for (PlayerComplete player : this.players) {
			if (player.getName().equals(info.getName())) {
				return player;
			}
		}
		throw new RuntimeException("Player " + info.getName() + "does not exist?");
	}

	@Override
	public void addPlayer(PlayerInfo info) {
		PlayerComplete player = new PlayerComplete(info.getName(), info.getPosition());
		player.setHero(new Blank()); // no heroes now..
		players.add(player);
	}

	@Override
	public PlayerComplete getNextPlayerAlive(PlayerComplete current) {
		int pos = current.getPosition();
		for (int i = pos + 1; i != pos; i = (i + 1) % players.size()) {
			if (players.get(i).isAlive())
				return players.get(i);
		}
		throw new RuntimeException("Can't find next player alive");// should not reach here
	}

	@Override
	public Deck getDeck() {
		return deck;
	}

	@Override
	public void start() {
		this.room.sendCommandToPlayers(
			this.players.stream().collect(
				Collectors.toMap(
					player -> player.getName(),
					player -> new EnterGameRoomGameClientCommand(getPlayersInfo(), player.getPlayerInfo())
				)
			)
		);
		for (PlayerComplete player : players) {
			player.registerCardOnHandListener(new ServerInGameCardOnHandListener(player.getName(), playerNames, room));
			player.registerEquipmentListener(new ServerInGameEquipmentListener(player.getName(), playerNames, room));
			player.registerHealthListener(new ServerInGameHealthListener(player.getName(), playerNames, room));
			player.setHero(new Blank()); // no heroes now..
		}
		this.controllers.push(new TurnGameController(room));
		for (PlayerComplete player : players) {
			player.addCards(deck.drawMany(4));
		}
		getGameController().proceed();
	}

	@Override
	public PlayerComplete findPlayer(Predicate<PlayerComplete> predicate) {
		for (PlayerComplete player : this.players) {
			if (predicate.test(player)) {
				return player;
			}
		}
		throw new RuntimeException("No player found for predicate");
	}

	@Override
	public void drawCards(PlayerComplete player, int amount) {
		player.addCards(deck.drawMany(amount));
	}

	@Override
	public GameController getGameController() {
		return controllers.peek();
	}

	@Override
	public void pushGameController(GameController controller) {
		// TODO checks and stuff?
		controllers.push(controller);
	}

}
