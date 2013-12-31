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

public class WineOperation implements Operation
{
	private IncreaseOfHealth update;
	private PlayerInfo source;
	private Card wine;
	private Event next;
	public WineOperation(PlayerInfo player, Card wine, Event e)
	{
		source = player;
		next = e;
		this.wine = wine;
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
		player.setCardOnHandSelected(wine, false);
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		player.setOperation(null);
		player.setCardOnHandSelected(wine, false);
		player.setCancelEnabled(false);
		if(player.isDying())
		{
			update = new IncreaseOfHealth(source,next);
			player.sendToMaster(new UseOfCards(source,wine,update));
		}
		else
		{
			player.useWine();
			player.sendToMaster(new UseOfCards(source,wine,next));
		}
	}

}
