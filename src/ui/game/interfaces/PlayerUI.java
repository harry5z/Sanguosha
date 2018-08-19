package ui.game.interfaces;

import core.player.PlayerSimple;

public interface PlayerUI extends Activatable {

	public PlayerSimple getPlayer();

	public void showCountdownBar();

	public void setWineUsed(boolean used);

	public void setFlipped(boolean flipped);
	
	public void setChained(boolean chained);

}
