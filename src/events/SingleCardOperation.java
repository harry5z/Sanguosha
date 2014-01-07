package events;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.UseOfCards;
import core.Card;
import core.Framework;
import core.Operation;
import core.PlayerInfo;
import core.Update;

public class SingleCardOperation implements Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8013540053622863152L;
	private Update update;
	private PlayerInfo source;
	private PlayerInfo target;
	private Card card;
	
	public SingleCardOperation(PlayerInfo source, Card card,Update next)
	{
		this.source = source;
		this.card = card;
		this.update = next;
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
		player.setConfirmEnabled(false);
		player.setCancelEnabled(false);
		player.setCardOnHandSelected(card, false);	
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		player.setOperation(null);
		player.setCardOnHandSelected(card, false);
		player.setCancelEnabled(false);
		player.sendToMaster(new UseOfCards(source,card,update));		
	}

}
