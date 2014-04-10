package core;

import heroes.Blank;

import java.util.ArrayList;
import java.util.List;

import listener.FrameworkListener;
import net.Server;
import player.Player;
import player.PlayerServerSimple;
import update.DrawCardsFromDeck;
import update.Stage;
import update.Update;
/**
 * The game framework. Currently only the original game (Emperor-loyalist-rebel-usurper), but in the future
 * may add other kinds (1v1, 3v3, Total War, etc.)
 * @author Harry
 *
 */
public class FrameworkImpl implements Framework4Gui, Framework
{
	private List<PlayerServerSimple> players;
	private Server master;//master server
	private Deck deck;//deck, currently only original game deck as well
	private FrameworkListener listener;//not used
	public FrameworkImpl(Server master)
	{
		this.master = master;
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
			if(player.matches(p))
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
			master.sendToAllClients(new DrawCardsFromDeck(p,deck.drawMany(4),deck.getDeckSize()));
		Stage start = new Stage(players.get(0).getPlayerInfo(),Stage.TURN_START_BEGINNING);
		master.sendToAllClients(start);
		listener.onGameStarted();
	}
	@Override
	public void reset()
	{
		deck = null;
		players = new ArrayList<PlayerServerSimple>();
		listener.onGameReset();
	}
	@Override
	public void sendToAllClients(Update update)
	{
		master.sendToAllClients(update);
	}
	@Override
	public void registerFrameworkListener(FrameworkListener listener)
	{
		this.listener = listener;
	}
}
