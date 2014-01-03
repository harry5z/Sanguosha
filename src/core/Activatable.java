package core;

import player.PlayerOriginalClientComplete;

public interface Activatable
{
	/**
	 * returns an operation corresponding to this card, with the next update set to be "next"
	 * @param player
	 * @param next
	 * @return the corresponding operation
	 */
	public Operation onActivatedBy(PlayerOriginalClientComplete player, Update next);
	/**
	 * decides whether the card is activatable by player during TURN_DEAL
	 * @param player
	 * @return true if activatable, false if not
	 */
	public boolean isActivatableBy(PlayerOriginalClientComplete player);
}
