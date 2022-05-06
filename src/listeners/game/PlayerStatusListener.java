package listeners.game;

import java.util.Set;

import core.player.Player;
import core.player.PlayerState;

public interface PlayerStatusListener {

	public void onWineUsed();
	
	public void onAttackUsed(Set<? extends Player> targets);
	
	public void onSetAttackLimit(int limit);
	
	public void onSetAttackUsed(int amount);
	
	public void onSetWineUsed(int amount);
	
	public void onResetWineEffective();
	
	public void onFlip(boolean flipped);
	
	public void onChained(boolean chained);
	
	public void onPlayerStateUpdated(PlayerState state, int value);
	
}
