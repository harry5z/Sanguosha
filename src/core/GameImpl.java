package core;

import heroes.original.Blank;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import listeners.server.ServerInGameCardOnHandListener;
import listeners.server.ServerInGameEquipmentListener;
import listeners.server.ServerInGameHealthListener;
import net.server.GameConfig;
import net.server.GameRoom;
import player.PlayerComplete;

import commands.game.client.EnterGameRoomGameClientCommand;
/**
 * The game framework. Currently only the original game (Emperor-loyalist-rebel-usurper), but in the future
 * may add other kinds (1v1, 3v3, Total War, etc.)
 * @author Harry
 *
 */
public class GameImpl implements Game {
	private List<PlayerComplete> players;
	private Set<String> playerNames;
	private GameRoom room; // the room where the game is held
	private Deck deck;//deck, currently only original game deck as well
	private GameConfig config;
	
	public GameImpl(GameRoom room, GameConfig config, List<PlayerInfo> playerInfos) {
		this.room = room;
		this.config = config;
		this.deck = new Deck(config.getDeckPacks());
		this.players = playerInfos.stream().map(info -> new PlayerComplete(info.getName(), info.getPosition())).collect(Collectors.toList());
		this.playerNames = playerInfos.stream().map(info -> info.getName()).collect(Collectors.toSet());
		for (PlayerComplete player : players) {
			player.setHero(new Blank()); // no heroes now..
			player.registerCardOnHandListener(new ServerInGameCardOnHandListener(player.getName(), playerNames, room));
			player.registerEquipmentListener(new ServerInGameEquipmentListener(player.getName(), playerNames, room));
			player.registerHealthListener(new ServerInGameHealthListener(player.getName(), playerNames, room));
		}
	}

	@Override
	public List<PlayerInfo> getPlayers() {
		return this.players.stream().map(player -> player.getPlayerInfo()).collect(Collectors.toList());
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
	public PlayerInfo getNextPlayerAlive(PlayerInfo current)
	{
		int pos = current.getPosition();
		for(int i = pos+1;i != pos;i++)
		{
			if(i == players.size())
				i = 0;
			if(players.get(i).isAlive())
				return players.get(i).getPlayerInfo();
		}
		return null;//should not reach here
	}
	@Override
	public Deck getDeck() {
		return deck;
	}
	@Override
	public void start() {
		room.sendCommandToPlayers(
			this.players.stream().collect(
				Collectors.toMap(
					player -> player.getName(), 
					player -> new EnterGameRoomGameClientCommand(getPlayers(), player.getPlayerInfo())
				)
			)
		);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (PlayerComplete player : players) {
			player.addCards(deck.drawMany(4));
		}
	}

}
