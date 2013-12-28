package core;

import java.util.ArrayList;

import update.Update;

public class Framework 
{
	private ArrayList<Player> players;
	private Deck deck;
	
	private Player currentPlayer;
	public Framework()
	{
		deck = new Deck(true,true);
		players = new ArrayList<Player>();
	}
	public ArrayList<Player> getPlayers() 
	{
		ArrayList<Player> temp = new ArrayList<Player>();
		for(Player p : players)
			temp.add(p);
		return temp;
	}
	public void addPlayer(Player player)
	{
		players.add(player);
	}
	public Player getCurrentPlayer() 
	{
		return currentPlayer;
	}
	public Deck getDeck()
	{
		return deck;
	}
	public void onNotified(Update note)
	{
		
	}
}
