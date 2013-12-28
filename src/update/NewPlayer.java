package update;

import java.util.ArrayList;

import core.Master;
import core.Player;

public class NewPlayer extends Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 196556915908683082L;
	private Player newPlayer;
	private ArrayList<Player> allPlayers;
	public NewPlayer(Player player)
	{
		newPlayer = player;
	}
	@Override
	public void masterOperation(Master master)
	{
		master.getFramework().addPlayer(newPlayer);
		allPlayers = master.getFramework().getPlayers();
		master.sendToAllClients(this);
	}
	@Override
	public void playerOperation(Player player) 
	{
		if(player.equals(newPlayer))
		{
			for(Player p : allPlayers)
				if(!p.equals(player))
					player.addOtherPlayer(p);
		}
		else
		{
			player.addOtherPlayer(newPlayer);
		}

	}
	public int size()
	{
		return allPlayers.size();
	}
}
