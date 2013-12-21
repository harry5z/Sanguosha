package core;

import java.util.ArrayList;

import update.Update;

public abstract class Framework 
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
		return players;
	}

	public Player getCurrentPlayer() 
	{
		return currentPlayer;
	}
	public Deck getDeck()
	{
		return deck;
	}
	
	public void onPlayerAdded(Player player)
	{
		
	}
	public void onNotified(Update note)
	{
		
	}
}
