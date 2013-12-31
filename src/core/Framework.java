package core;

import heroes.Blank;

import java.util.ArrayList;
import java.util.Stack;

import listener.FrameworkListener;
import net.Master;
import player.PlayerOriginalMasterSimple;
import update.DrawCardsFromDeck;
import update.StageUpdate;

public class Framework 
{
	private ArrayList<PlayerOriginalMasterSimple> players;
	private Master master;
	private Deck deck;
	private FrameworkListener listener;
	private Stack<Event> events;
	public Framework(Master master)
	{
		this.master = master;
		deck = new Deck(true,true);
		players = new ArrayList<PlayerOriginalMasterSimple>();
		events = new Stack<Event>();
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
		PlayerOriginalMasterSimple p = new PlayerOriginalMasterSimple(player.getName()+" "+position,position);
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
	public PlayerOriginalMasterSimple findMatch(PlayerInfo p)
	{
		for(PlayerOriginalMasterSimple player : players)
			if(player.isEqualTo(p))
				return player;
		return null;
	}
	public Stack<Event> getEventStack()
	{
		return events;
	}
	public void addEvent(Event e)
	{
		events.push(e);
	}
	public Deck getDeck()
	{
		return deck;
	}
	public void start()
	{
		for(PlayerInfo p : getPlayers())
			master.sendToAllClients(new DrawCardsFromDeck(p,deck.drawMany(4)));
		System.out.println("Finished card drawing");
		StageUpdate start = new StageUpdate(players.get(0).getPlayerInfo(),StageUpdate.TURN_START_BEGINNING);
		master.sendToAllClients(start);
		System.out.println("Starting");
	}
	public void sendToAllClients(Update update)
	{
		master.sendToAllClients(update);
	}
	public void registerFrameworkListener(FrameworkListener listener)
	{
		this.listener = listener;
	}
}
