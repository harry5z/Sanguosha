package ui.game.interfaces;

import core.heroes.Hero;
import core.player.PlayerCompleteClient;

public interface HeroUI extends PlayerUI {

	public Hero getHero();

	public void setPlayer(PlayerCompleteClient player);

}
