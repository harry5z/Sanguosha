package events;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.IncreaseOfHealth;
import update.UseOfCards;
import core.Card;
import core.Event;
import core.Framework;
import core.Operation;
import core.PlayerInfo;

public class PeachOperation implements Operation
{
	private IncreaseOfHealth update;
	private PlayerInfo source;
	private Card peach;
	public PeachOperation(PlayerInfo player, Card peach, Event e)
	{
		source = player;
		update = new IncreaseOfHealth(player,e);
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
		player.setCardOnHandSelected(peach, false);
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		player.setOperation(null);
		player.setCardOnHandSelected(peach, false);
		player.setCancelEnabled(false);
		player.sendToMaster(new UseOfCards(source,peach,update));
	}

}
