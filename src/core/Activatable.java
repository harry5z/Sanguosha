package core;

import player.PlayerOriginalClientComplete;

public interface Activatable
{
	public void onActivatedBy(PlayerOriginalClientComplete player);
	public boolean isActivatableBy(PlayerOriginalClientComplete player);
}
