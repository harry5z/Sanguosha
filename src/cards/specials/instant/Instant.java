package cards.specials.instant;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;
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
	public boolean isActivatableBy(PlayerClientComplete player) 
	{
		return true;
	}
	
	@Override
	public Operation onActivatedBy(PlayerClientComplete player,Update next)
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
	protected abstract Operation createOperation(PlayerClientComplete player,Update next);
}
