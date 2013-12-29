package core;

import java.util.ArrayList;

import player.PlayerOriginalMasterSimple;
import update.Update;

public class Framework 
{
	private ArrayList<PlayerOriginalMasterSimple> players;
	private Deck deck;
	
	private Player currentPlayer;
	public Framework()
	{
		deck = new Deck(true,true);
		players = new ArrayList<PlayerOriginalMasterSimple>();
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
		players.add(new PlayerOriginalMasterSimple(player.getName(),player.getPosition()));
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
