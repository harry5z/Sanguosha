package core.server.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import commands.game.client.EnterOriginalGameRoomGameClientCommand;
import core.Deck;
import core.event.game.GameEvent;
import core.event.handlers.EventHandler;
import core.heroes.original.Blank;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.ConnectionController;
import core.server.GameRoom;
import core.server.game.controllers.GameController;
import core.server.game.controllers.TurnGameController;
import exceptions.server.game.GameFlowInterruptedException;
import listeners.game.server.ServerInGameCardDisposalListener;
import listeners.game.server.ServerInGameCardOnHandListener;
import listeners.game.server.ServerInGameEquipmentListener;
import listeners.game.server.ServerInGameHealthListener;
import listeners.game.server.ServerInGamePlayerStatusListener;
import utils.Log;

/**
 * The game framework. Currently only the original game
 * (Emperor-loyalist-rebel-usurper), but in the future may add other kinds (1v1,
 * 3v3, Total War, etc.)
 * 
 * @author Harry
 *
 */
public class GameImpl implements Game {
	private static final String TAG = "Game";
	private List<PlayerCompleteServer> players;
	private Set<String> playerNames;
	private GameRoom room; // the room where the game is held
	private Deck deck;// deck, currently only original game deck as well
	private GameConfig config;
	private Stack<GameController> controllers;
	private TurnGameController turnController;
	/*
	 * A map from event types to a map from players to event handlers of that type. Implicitly allowing up
	 * to only one handler for each player for each event type
	 */
	private Map<Class<? extends GameEvent>, Map<PlayerCompleteServer, EventHandlerStack>> gameEventHandlers;

	private class EventHandlerStack {
		
		private class Node {
			Node next;
			EventHandler<? extends GameEvent> handler;
			
			public Node(EventHandler<? extends GameEvent> handler) {
				this.handler = handler;
				this.next = null;
			}
		}
		
		Node root;
		
		public EventHandlerStack(EventHandler<? extends GameEvent> handler) {
			this.root = new Node(handler);
		}
		
		void push(EventHandler<? extends GameEvent> handler) {
			Node node = new Node(handler);
			node.next = this.root;
			this.root = node;
		}
		
		void remove(EventHandler<? extends GameEvent> handler) {
			if (this.root == null) {
				Log.log(TAG, "no matching handler found: no handler for the event");
				return;
			}
			
			if (this.root.handler.equals(handler)) {
				this.root = this.root.next;
				return;
			}
			
			for (Node current = this.root, next = current.next; next != null; current = next, next = next.next) {
				if (next.handler.equals(handler)) {
					current.next = next.next;
					handler.onRemoved(GameImpl.this, GameImpl.this.room);
					return;
				}
			}
			Log.log(TAG, "no matching handler found");
		}
		
		@SuppressWarnings("unchecked")
		<T extends GameEvent> void handle(T event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
			for (Node current = this.root; current != null; current = current.next) {
				((EventHandler<T>)current.handler).handle(event, game, connection);
			}
		}
	}
	
	public GameImpl(GameRoom room, GameConfig config, List<PlayerInfo> playerInfos) {
		this.room = room;
		this.config = config;
		this.deck = new Deck(config.getDeckPacks());
		this.players = playerInfos.stream()
			.map(info -> new PlayerCompleteServer(info.getName(), info.getPosition()))
			.collect(Collectors.toList());

		this.playerNames = playerInfos.stream().map(info -> info.getName()).collect(Collectors.toSet());
		this.controllers = new Stack<>();
		this.gameEventHandlers = new HashMap<>();
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
	public List<PlayerCompleteServer> getPlayersAlive() {
		return players.stream().filter(player -> player.isAlive()).collect(Collectors.toList());
	}
	
	@Override
	public int getNumberOfPlayersAlive() {
		int count = 0;
		for (PlayerCompleteServer player : players) {
			if (player.isAlive()) {
				count++;
			}
		}
		return count;
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
	
	private PlayerCompleteServer getNextPlayer(PlayerCompleteServer current) {
		int pos = current.getPosition();
		pos = (pos + 1) % this.players.size();
		return this.players.get(pos);
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
	public PlayerCompleteServer getCurrentPlayer() {
		return this.turnController.getCurrentPlayer();
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
			player.setHero(new Blank()); // TODO: add heroes
			player.onGameReady(this);
		}
		this.turnController = new TurnGameController(room);
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
		return this.controllers.isEmpty() ? (T) this.turnController : (T) controllers.peek();
	}
	@Override
	public void pushGameController(GameController controller) {
		// TODO checks and stuff?
		controllers.push(controller);
	}
	
	@Override
	public void pushNextGameController(GameController controller) {
		controllers.add(1, controller);
	}
	
	@Override
	public void popGameController() {
		controllers.pop();
	}
	
	@Override
	public <T extends GameEvent> void registerEventHandler(EventHandler<T> handler) {
		Class<T> c = handler.getEventClass();
		Map<PlayerCompleteServer, EventHandlerStack> handlerStackMap = this.gameEventHandlers.get(c);
		if (handlerStackMap != null) {
			if (handlerStackMap.containsKey(handler.getPlayerSource())) {
				handlerStackMap.get(handler.getPlayerSource()).push(handler);
			} else {
				handlerStackMap.put(handler.getPlayerSource(), new EventHandlerStack(handler));
			}
		} else {
			Map<PlayerCompleteServer, EventHandlerStack> handlerMap = new HashMap<>();
			handlerMap.put(handler.getPlayerSource(), new EventHandlerStack(handler));
			this.gameEventHandlers.put(c, handlerMap);
		}
	}
	
	@Override
	public <T extends GameEvent> void removeEventHandler(EventHandler<T> handler) {
		Class<T> c = handler.getEventClass();
		Map<PlayerCompleteServer, EventHandlerStack> map = this.gameEventHandlers.get(c);
		if (map == null) {
			throw new RuntimeException("handler not found, never registered in map");
		}
		EventHandlerStack stack = map.get(handler.getPlayerSource());
		if (stack == null) {
			throw new RuntimeException("handler not found, never registered for player");
		}
		stack.remove(handler);
	}
	
	@Override
	public <T extends GameEvent> void emit(T event) throws GameFlowInterruptedException {
		if (this.gameEventHandlers.containsKey(event.getClass())) {
			Map<PlayerCompleteServer, EventHandlerStack> handlers = this.gameEventHandlers.get(event.getClass());
			PlayerCompleteServer currentCheckedPlayer = this.getCurrentPlayer();
			PlayerCompleteServer initialPlayer = currentCheckedPlayer;
			
			do {
				if (handlers.containsKey(currentCheckedPlayer)) {
					handlers.get(currentCheckedPlayer).handle(event, this, this.room);
				}
				currentCheckedPlayer = this.getNextPlayer(currentCheckedPlayer);
			} while (initialPlayer != currentCheckedPlayer);
			
		}
	}

}
