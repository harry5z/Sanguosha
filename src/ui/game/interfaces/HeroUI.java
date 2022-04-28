package ui.game.interfaces;

import core.heroes.Hero;
import core.player.PlayerCompleteClient;
import listeners.game.HeroListener;
import listeners.game.PlayerStatusListener;

public interface HeroUI<T extends Hero> extends PlayerUI, PlayerStatusListener, HeroListener {

	public T getHero();

	public void setPlayer(PlayerCompleteClient player);

}
