package ui.game.interfaces;

import core.heroes.Hero;
import core.player.PlayerCompleteClient;
import listeners.game.HeroListener;
import listeners.game.PlayerStatusListener;

public interface HeroUI extends PlayerUI, PlayerStatusListener, HeroListener {

	public Hero getHero();

	public void setPlayer(PlayerCompleteClient player);

}
