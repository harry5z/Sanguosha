package listeners.game;

import core.player.PlayerState;

public interface PlayerStatusListener {

	public void onAttackUsed();
	
	public void onSetAttackUsed(int amount);
	
	public void onSetWineUsed(int amount);
	
	public void onFlip(boolean flipped);
	
	public void onChained(boolean chained);
	
	public void onPlayerStateUpdated(PlayerState state, int value);
	
}
