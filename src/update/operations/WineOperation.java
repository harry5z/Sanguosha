package update.operations;

import player.PlayerClientComplete;
import player.PlayerOriginal;
import update.IncreaseOfHealth;
import update.Update;
import update.UseOfCards;
import cards.Card;
import core.Framework;
import core.PlayerInfo;

/**
 * Operation of use of wine. Wine can be used either to increase attack damage once,
 * or used to save oneself when dying
 * @author Harry
 *
 */
public class WineOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3919164368836659196L;
	private PlayerInfo source;
	private Card wine;
	public WineOperation(PlayerInfo player, Card wine, Update next)
	{
		super(next);
		source = player;
		this.wine = wine;
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
		player.getGameListener().setConfirmEnabled(false);
		player.getGameListener().setCancelEnabled(false);
		player.getGameListener().setCardSelected(wine, false);
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player) 
	{
		player.getGameListener().setCardSelected(wine, false);
		player.getGameListener().setCancelEnabled(false);
		if(player.isDying())//can increase health by 1
		{
			player.sendToMaster(new UseOfCards(source,wine,new IncreaseOfHealth(source,getNext())));
		}
		else//can increase attack damage by 1
		{
			player.useWine();
			player.sendToMaster(new UseOfCards(source,wine,getNext()));
		}
	}

}
