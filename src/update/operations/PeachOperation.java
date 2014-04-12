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
 * Operation of use of peach
 * @author Harry
 *
 */
public class PeachOperation extends Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1723063004362265957L;
	private PlayerInfo source;
	private PlayerInfo target;
	private Card peach;
	public PeachOperation(PlayerInfo player, Card peach, Update next)
	{
		super(new IncreaseOfHealth(player,next));
		source = player;
		target = null;
		this.peach = peach;
	}
	/**
	 * used when saving others during near-death event
	 * @param player
	 */
	public void setTarget(PlayerInfo player)
	{
		target = player;
		((IncreaseOfHealth)getNext()).setTarget(target);
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
		player.getGameListener().setCardSelected(peach, false);
	}

	@Override
	public void onConfirmedBy(PlayerClientComplete player) 
	{
		player.setOperation(null);
		player.getGameListener().setCardSelected(peach, false);
		player.getGameListener().setCancelEnabled(false);
		player.sendToMaster(new UseOfCards(source,peach,getNext()));
	}

}
