package cards.specials.instant;

import commands.Command;
import commands.operations.Operation;
import player.PlayerComplete;
import cards.specials.Special;

public abstract class Instant extends Special
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9048643013272059145L;

	public Instant(int num, Suit suit)
	{
		super(num, suit, true);
	}
	
	@Override
	public boolean isActivatableBy(PlayerComplete player) 
	{
		return true;
	}
	
	@Override
	public Operation onActivatedBy(PlayerComplete player,Command next)
	{
		player.getGameListener().setCardSelected(this, true);
		player.getGameListener().setCancelEnabled(true);
		return createOperation(player,next);
	}
	
	/**
	 * A template that allows each card to create its corresponding operation. <br>
	 * By default 
	 * <code>
	 * <ul>
	 * <li> player.getGameListener().setCardSelected(this, true); </li>
	 * <li> player.getGameListener().setCancelEnabled(true); </li>
	 * </ul>
	 * </code>
	 * are called before this. If more notifications are necessary, do them before returning
	 * the new operation
	 * @param player
	 * @param next
	 * @return
	 */
	protected abstract Operation createOperation(PlayerComplete player,Command next);
}
