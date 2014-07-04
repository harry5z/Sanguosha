package core;

import heroes.original.Blank;

import java.util.ArrayList;
import java.util.List;

import net.server.Room;
import net.server.Server;
import commands.Command;
import commands.DrawCardsFromDeck;
import commands.Stage;
import player.Player;
import player.PlayerServerSimple;
/**
 * The game framework. Currently only the original game (Emperor-loyalist-rebel-usurper), but in the future
 * may add other kinds (1v1, 3v3, Total War, etc.)
 * @author Harry
 *
 */
public class GameImpl implements Game
{
	private List<PlayerServerSimple> players;
	private Room room; // the room where the game is held
	private Deck deck;//deck, currently only original game deck as well
	public GameImpl(Room room)
	{
		this.room = room;
		players = new ArrayList<PlayerServerSimple>();
	}

	@Override
	public ArrayList<PlayerInfo> getPlayers() 
	{
		ArrayList<PlayerInfo> temp = new ArrayList<PlayerInfo>();
		for(Player p : players)
			temp.add(new PlayerInfo(p.getName(),p.getPosition()));
		return temp;
	}
	@Override
	public void addPlayer(PlayerInfo player)
	{
		int position = players.size()+1;
		PlayerServerSimple p = new PlayerServerSimple(player.getName()+" "+position,position);
		p.setHero(new Blank());
		players.add(p);
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
	public PlayerServerSimple findMatch(PlayerInfo p)
	{
		for(PlayerServerSimple player : players)
			if(player.equals(p))
				return player;
		return null;
	}
	@Override
	public Deck getDeck()
	{
		return deck;
	}
	@Override
	public void start()
	{
		deck = new Deck(true,true);
		for(PlayerInfo p : getPlayers())
			room.sendToAllClients(new DrawCardsFromDeck(p,deck.drawMany(4),deck.getDeckSize()));
		Stage start = new Stage(players.get(0).getPlayerInfo(),Stage.TURN_START_BEGINNING);
		room.sendToAllClients(start);
	}
	@Override
	public void reset()
	{
		deck = null;
		players = new ArrayList<PlayerServerSimple>();
	}
	@Override
	public void sendToAllClients(Command update)
	{
		master.sendToAllClients(update);
	}
}
