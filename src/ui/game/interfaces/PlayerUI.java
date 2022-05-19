package ui.game.interfaces;

import core.player.PlayerSimple;

public interface PlayerUI extends Activatable {

	public PlayerSimple getPlayer();

	public void showCountdownBar(int timeMS);
	
	public void stopCountdown();
	
	public void setWineUsed(boolean used);

	public void setFlipped(boolean flipped);
	
	public void setChained(boolean chained);

}
