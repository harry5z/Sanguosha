package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import core.Framework;
import core.Player;
import core.PlayerInfo;

public class NewPlayer implements Update
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 196556915908683082L;
	private PlayerInfo newPlayer;
	private ArrayList<PlayerInfo> allPlayers;
	public NewPlayer(Player player)
	{
		newPlayer = player.getPlayerInfo();
	}
	@Override
	public void frameworkOperation(Framework framework)
	{
		framework.addPlayer(newPlayer);
		allPlayers = framework.getPlayers();
		framework.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		int newPos = allPlayers.size();
		if(player.matches(newPlayer))
		{
			player.setPosition(newPos);
			for(PlayerInfo p : allPlayers)
			{
				if(!player.matches(p))
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
