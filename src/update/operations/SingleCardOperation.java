package update.operations;

import player.PlayerClientComplete;
import player.PlayerOriginal;
import update.Update;
import update.UseOfCards;
import cards.Card;
import core.Framework;
import core.PlayerInfo;

public class SingleCardOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8013540053622863152L;
	private PlayerInfo source;
	private PlayerInfo target;
	private Card card;
	
	public SingleCardOperation(PlayerInfo source, Card card,Update next)
	{
		super(next);
		this.source = source;
		this.card = card;
	}
	@Override
	public void frameworkOperation(Framework framework) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerOperation(PlayerClientComplete player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerSelected(PlayerClientComplete operator,
			PlayerOriginal player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCardSelected(PlayerClientComplete operator, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelledBy(PlayerClientComplete player) 
	{
		player.setConfirmEnabled(false);
		player.setCancelEnabled(false);
		player.setCardOnHandSelected(card, false);	
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player) 
	{
		player.setOperation(null);
		player.setCardOnHandSelected(card, false);
		player.setCancelEnabled(false);
		player.sendToMaster(new UseOfCards(source,card,getNext()));		
	}

}
