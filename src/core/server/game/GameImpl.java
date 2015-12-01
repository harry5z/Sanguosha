package core.server.game;

import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import commands.game.client.EnterOriginalGameRoomGameClientCommand;
import core.Deck;
import core.heroes.original.Blank;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.controllers.GameController;
import core.server.game.controllers.TurnGameController;
import listeners.game.server.ServerInGameCardDisposalListener;
import listeners.game.server.ServerInGameCardOnHandListener;
import listeners.game.server.ServerInGameEquipmentListener;
import listeners.game.server.ServerInGameHealthListener;
import listeners.game.server.ServerInGamePlayerStatusListener;

/**
 * The game framework. Currently only the original game
 * (Emperor-loyalist-rebel-usurper), but in the future may add other kinds (1v1,
 * 3v3, Total War, etc.)
 * 
 * @author Harry
 *
 */
public class GameImpl implements Game {
	private List<PlayerCompleteServer> players;
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
			.map(info -> new PlayerCompleteServer(info.getName(), info.getPosition()))
			.collect(Collectors.toList());

		this.playerNames = playerInfos.stream().map(info -> info.getName()).collect(Collectors.toSet());
		this.controllers = new Stack<>();
	}

	@Override
	public List<PlayerInfo> getPlayersInfo() {
		return this.players.stream().map(player -> player.getPlayerInfo()).collect(Collectors.toList());
	}

	@Override
	public List<PlayerCompleteServer> getPlayers() {
		return players;
	}

	@Override
	public PlayerCompleteServer findPlayer(PlayerInfo info) {
		for (PlayerCompleteServer player : this.players) {
			if (player.getName().equals(info.getName())) {
				return player;
			}
		}
		throw new RuntimeException("Player " + info.getName() + "does not exist?");
	}

	@Override
	public void addPlayer(PlayerInfo info) {
		PlayerCompleteServer player = new PlayerCompleteServer(info.getName(), info.getPosition());
		player.setHero(new Blank()); // no heroes now..
		players.add(player);
	}

	@Override
	public PlayerCompleteServer getNextPlayerAlive(PlayerCompleteServer current) {
		int pos = current.getPosition();
		for (int i = (pos + 1) % players.size(); i != pos; i = (i + 1) % players.size()) {
			if (players.get(i).isAlive()) {
				return players.get(i);
			}
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
					player -> new EnterOriginalGameRoomGameClientCommand(getPlayersInfo(), player.getPlayerInfo())
				)
			)
		);
		for (PlayerCompleteServer player : players) {
			player.registerCardOnHandListener(new ServerInGameCardOnHandListener(player.getName(), playerNames, room));
			player.registerEquipmentListener(new ServerInGameEquipmentListener(player, playerNames, room));
			player.registerHealthListener(new ServerInGameHealthListener(player.getName(), playerNames, room));
			player.registerCardDisposalListener(new ServerInGameCardDisposalListener(player.getName(), playerNames, room));
			player.registerPlayerStatusListener(new ServerInGamePlayerStatusListener(player.getName(), playerNames, room));
			player.setHero(new Blank()); // no heroes now..
		}
		this.controllers.push(new TurnGameController(room));
		for (PlayerCompleteServer player : players) {
			player.addCards(deck.drawMany(4));
		}
		getGameController().proceed();
	}

	@Override
	public PlayerCompleteServer findPlayer(Predicate<PlayerCompleteServer> predicate) {
		for (PlayerCompleteServer player : this.players) {
			if (predicate.test(player)) {
				return player;
			}
		}
		throw new RuntimeException("No player found for predicate");
	}

	@Override
	public void drawCards(PlayerCompleteServer player, int amount) {
		player.addCards(deck.drawMany(amount));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends GameController> T getGameController() {
		return (T) controllers.peek();
	}
	@Override
	public void pushGameController(GameController controller) {
		// TODO checks and stuff?
		controllers.push(controller);
	}
	
	@Override
	public void popGameController() {
		controllers.pop();
	}

}
