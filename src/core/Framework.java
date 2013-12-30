package core;

import heroes.Blank;

import java.util.ArrayList;
import java.util.Stack;

import net.Master;
import player.PlayerOriginalMasterSimple;

public class Framework 
{
	private ArrayList<PlayerOriginalMasterSimple> players;
	private Master master;
	private Deck deck;
	
	private Stack<Event> events;
	private Player currentPlayer;
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
		PlayerOriginalMasterSimple p = new PlayerOriginalMasterSimple(player.getName(),player.getPosition());
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
	public Player getCurrentPlayer() 
	{
		return currentPlayer;
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
	public void sendToAllClients(Update update)
	{
		master.sendToAllClients(update);
	}
}
