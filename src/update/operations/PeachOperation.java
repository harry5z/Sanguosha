package update.operations;

import cards.Card;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.IncreaseOfHealth;
import update.Update;
import update.UseOfCards;
import core.Framework;
import core.PlayerInfo;

/**
 * Operation of use of peach
 * @author Harry
 *
 */
public class PeachOperation implements Operation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1723063004362265957L;
	private IncreaseOfHealth update;
	private PlayerInfo source;
	private PlayerInfo target;
	private Card peach;
	public PeachOperation(PlayerInfo player, Card peach, Update next)
	{
		source = player;
		target = null;
		update = new IncreaseOfHealth(player,next);
		this.peach = peach;
	}
	/**
	 * used when saving others during near-death event
	 * @param player
	 */
	public void setTarget(PlayerInfo player)
	{
		target = player;
		update.setTarget(target);
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
