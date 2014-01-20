package events.special_events;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.DrawCardsFromDeck;
import update.Update;
import update.UseOfCards;
import core.Card;
import core.PlayerInfo;

public class CreationOperation extends SpecialOperation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1410334098472333831L;
	private PlayerInfo source;
	private Card creation;
	
	public CreationOperation(PlayerInfo source, PlayerInfo turnPlayer, Card creation, Update next)
	{
		super(next, turnPlayer);
		this.source = source;
		this.creation = creation;
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
		player.setCardOnHandSelected(creation, false);	
	}

	@Override
	public void onConfirmedBy(PlayerOriginalClientComplete player) 
	{
		player.setOperation(null);
		player.setCardOnHandSelected(creation, false);
		player.setCancelEnabled(false);
		player.sendToMaster(new UseOfCards(source,creation,this));
	}

	@Override
	protected void playerOpEffect(PlayerOriginalClientComplete player) 
	{
		if(player.isEqualTo(source))
		{
			this.nextStage();
			player.sendToMaster(new DrawCardsFromDeck(player.getPlayerInfo(),2,this));
		}
	}

}
