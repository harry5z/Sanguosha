package events;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.IncreaseOfHealth;
import core.Card;
import core.Framework;
import core.Operation;
import core.PlayerInfo;

public class PeachOperation implements Operation
{
	private IncreaseOfHealth update;
	private Card peach;
	public PeachOperation(PlayerInfo player, Card peach)
	{
		update = new IncreaseOfHealth(player);
		this.peach = peach;
	}
	@Override
	public void frameworkOperation(Framework framework) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerOperation(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerSelected(PlayerOriginalClientComplete operator,
			PlayerOriginal player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelledBy(PlayerOriginalClientComplete player) 
	{
		player.setOperation(null);
		player.setConfirmEnabled(false);
		player.setCancelEnabled(false);
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		player.setOperation(null);
		player.setCardOnHandSelected(peach, false);
		player.setCancelEnabled(false);
	}

}
