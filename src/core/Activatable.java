package core;

import player.PlayerOriginalClientComplete;

public interface Activatable
{
	public Operation onActivatedBy(PlayerOriginalClientComplete player, Update next);
	public boolean isActivatableBy(PlayerOriginalClientComplete player);
}
