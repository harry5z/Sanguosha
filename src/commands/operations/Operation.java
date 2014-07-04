package commands.operations;

import commands.Command;
import cards.Card;
import player.PlayerOriginal;
import player.PlayerComplete;
/**
 * An operation that listens to user actions (confirm, cancel, select cards/targets, etc.)
 * @author Harry
 *
 */
public abstract class Operation extends Command
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5751768653876815254L;
	public Operation(Command next) 
	{
		super(next);
	}

	/**
	 * called when a player is selected as target by operator
	 * @param operator
	 * @param player
	 */
	public abstract void onPlayerSelected(PlayerComplete operator,PlayerOriginal player);
	
	/**
	 * called when a card is selected by operator
	 * @param card
	 */
	public abstract void onCardSelected(PlayerComplete operator, Card card);
	/**
	 * called when cancel is clicked by player
	 * @param player
	 */
	public abstract void onCancelledBy(PlayerComplete player);
	/**
	 * called when confirm is clicked by player
	 * @param player
	 */
	public abstract void onConfirmedBy(PlayerComplete player);
}
