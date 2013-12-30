package update;

import java.util.ArrayList;

import net.Master;
import player.PlayerOriginalClientComplete;
import player.PlayerOriginalClientSimple;
import core.Card;
import core.Framework;
import core.Player;
import core.PlayerInfo;
import core.Update;

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
		newPlayer = new PlayerInfo(player.getName(),player.getPosition());
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
		if(player.isEqualTo(newPlayer))
		{
			for(PlayerInfo p : allPlayers)
				if(!player.isEqualTo(p))
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
