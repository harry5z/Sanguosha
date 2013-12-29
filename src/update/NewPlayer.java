package update;

import java.util.ArrayList;

import player.PlayerOriginalClientComplete;
import player.PlayerOriginalClientSimple;
import core.Card;
import core.Master;
import core.Player;
import core.PlayerInfo;

public class NewPlayer extends Update
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
	public void masterOperation(Master master)
	{
		master.getFramework().addPlayer(newPlayer);
		allPlayers = master.getFramework().getPlayers();
		master.sendToAllClients(this);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		if(player.equalTo(newPlayer))
		{
			for(PlayerInfo p : allPlayers)
				if(!player.equalTo(p))
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
	@Override
	public void onPlayerSelected(PlayerOriginalClientSimple player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCardSelected(Card card) {
		// TODO Auto-generated method stub
		
	}
}
