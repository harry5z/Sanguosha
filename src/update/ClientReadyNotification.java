package update;

import core.Player;

public class ClientReadyNotification extends Update
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2187633027793921017L;

	@Override
	public void playerOperation(Player player)
	{
		player.sendToMaster(new NewPlayer(player));
		
	}
	
}
