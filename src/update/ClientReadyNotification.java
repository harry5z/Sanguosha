package update;

import player.PlayerOriginalClientComplete;
import player.PlayerOriginalClientSimple;
import core.Card;
import core.Framework;
import core.Player;
import core.Update;

public class ClientReadyNotification implements Update
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2187633027793921017L;

	@Override
	public void playerOperation(PlayerOriginalClientComplete player)
	{
		player.sendToMaster(new NewPlayer(player));
	}

	@Override
	public void frameworkOperation(Framework framework) {
		// TODO Auto-generated method stub
		
	}
}
