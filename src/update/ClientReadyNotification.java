package update;

import player.PlayerOriginalClientComplete;
import player.PlayerOriginalClientSimple;
import core.Card;
import core.Player;

public class ClientReadyNotification extends Update
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
	public void onPlayerSelected(PlayerOriginalClientSimple player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardSelected(Card card) {
		// TODO Auto-generated method stub
		
	}
	
}
