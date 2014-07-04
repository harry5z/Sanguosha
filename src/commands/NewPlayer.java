package commands;

import java.util.ArrayList;

import player.Player;
import player.PlayerComplete;
import core.Game;
import core.PlayerInfo;

public class NewPlayer extends Command
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 196556915908683082L;
	private PlayerInfo newPlayer;
	private ArrayList<PlayerInfo> allPlayers;
	public NewPlayer(Player player)
	{
		super(null);
		newPlayer = player.getPlayerInfo();
	}
	@Override
	public void ServerOperation(Game framework)
	{
		framework.addPlayer(newPlayer);
		allPlayers = framework.getPlayers();
		framework.sendToAllClients(this);
	}
	@Override
	public void ClientOperation(PlayerComplete player) 
	{
		int newPos = allPlayers.size();
		if(player.equals(newPlayer))
		{
			player.setPosition(newPos);
			for(PlayerInfo p : allPlayers)
			{
				if(!player.equals(p))
					player.addOtherPlayer(p);
				else
					player.setName(p.getName());
			}
		}
		else
		{
			player.addOtherPlayer(new PlayerInfo(newPlayer.getName()+" "+newPos,newPos));
		}

	}
	public int size()
	{
		return allPlayers.size();
	}
}
