package update.operations;

import cards.Card;
import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;
import update.Update;
/**
 * An operation that listens to user actions (confirm, cancel, select cards/targets, etc.)
 * @author Harry
 *
 */
public interface Operation extends Update
{
	/**
	 * called when a player is selected as target by operator
	 * @param operator
	 * @param player
	 */
	public void onPlayerSelected(PlayerOriginalClientComplete operator,PlayerOriginal player);
	
	/**
	 * called when a card is selected by operator
	 * @param card
	 */
	public void onCardSelected(PlayerOriginalClientComplete operator, Card card);
	/**
	 * called when cancel is clicked by player
	 * @param player
	 */
	public void onCancelledBy(PlayerOriginalClientComplete player);
	/**
	 * called when confirm is clicked by player
	 * @param player
	 */
	public void onConfirmedBy(PlayerOriginalClientComplete player);
}
