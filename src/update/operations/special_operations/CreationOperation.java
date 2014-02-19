package update.operations.special_operations;

import cards.Card;
import player.PlayerOriginal;
import player.PlayerClientComplete;
import update.DrawCardsFromDeck;
import update.Update;
import update.UseOfCards;
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
		player.setCardOnHandSelected(creation, false);	
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player) 
	{
		player.setOperation(null);
		player.setCardOnHandSelected(creation, false);
		player.setCancelEnabled(false);
		player.sendToMaster(new UseOfCards(source,creation,this));
	}

	@Override
	protected void playerOpEffect(PlayerClientComplete player) 
	{
		if(player.matches(source))
		{
			this.nextStage();
			player.sendToMaster(new DrawCardsFromDeck(player.getPlayerInfo(),2,this));
		}
	}
	@Override
	public String getName()
	{
		return "Creation";
	}
}
