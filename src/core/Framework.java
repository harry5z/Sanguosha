package core;

import heroes.Blank;

import java.util.ArrayList;
import java.util.Stack;

import listener.FrameworkListener;
import net.Master;
import player.Player;
import player.PlayerMasterSimple;
import update.DrawCardsFromDeck;
import update.Stage;
import update.Update;
/**
 * The game framework. Currently only the original game (Emperor-loyalist-rebel-usurper), but in the future
 * may add other kinds (1v1, 3v3, Total War, etc.)
 * @author Harry
 *
 */
public class Framework 
{
	private ArrayList<PlayerMasterSimple> players;
	private Master master;//master server
	private Deck deck;//deck, currently only original game deck as well
	private FrameworkListener listener;//not used
	public Framework(Master master)
	{
		this.master = master;
		deck = new Deck(true,true);
		players = new ArrayList<PlayerMasterSimple>();
	}
	public ArrayList<PlayerInfo> getPlayers() 
	{
		ArrayList<PlayerInfo> temp = new ArrayList<PlayerInfo>();
		for(Player p : players)
			temp.add(new PlayerInfo(p.getName(),p.getPosition()));
		return temp;
	}
	public void addPlayer(PlayerInfo player)
	{
		int position = players.size()+1;
		PlayerMasterSimple p = new PlayerMasterSimple(player.getName()+" "+position,position);
		p.setHero(new Blank());
		players.add(p);
	}
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
	public PlayerMasterSimple findMatch(PlayerInfo p)
	{
		for(PlayerMasterSimple player : players)
			if(player.matches(p))
				return player;
		return null;
	}
	public Deck getDeck()
	{
		return deck;
	}
	/**
	 * start game
	 */
	public void start()
	{
		for(PlayerInfo p : getPlayers())
			master.sendToAllClients(new DrawCardsFromDeck(p,deck.drawMany(4),deck.getDeckSize()));
		Stage start = new Stage(players.get(0).getPlayerInfo(),Stage.TURN_START_BEGINNING);
		master.sendToAllClients(start);
	}
	/**
	 * send update to all players, default operation of an update
	 * @param update
	 */
	public void sendToAllClients(Update update)
	{
		master.sendToAllClients(update);
	}
	public void registerFrameworkListener(FrameworkListener listener)
	{
		this.listener = listener;
	}
}
