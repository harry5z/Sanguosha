package ui.game.interfaces;

import core.player.PlayerSimple;
import listeners.game.HeroListener;

public interface PlayerUI extends Activatable, HeroListener {

	public PlayerSimple getPlayer();

	public void showCountdownBar(int timeMS);
	
	public void stopCountdown();
	
}
