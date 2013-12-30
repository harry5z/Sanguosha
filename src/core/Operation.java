package core;

import player.PlayerOriginal;
import player.PlayerOriginalClientComplete;

public interface Operation extends Update
{
	public abstract void onPlayerSelected(PlayerOriginalClientComplete operator,PlayerOriginal player);
	
	/**
	 * usually used by skills and equipment for effects
	 * @param card
	 */
	public abstract void onCardSelected(PlayerOriginalClientComplete operator, Card card);
	
	public abstract void onCancelledBy(PlayerOriginalClientComplete player);
	public abstract void onConfirmedBy(PlayerOriginalClientComplete player);
}
