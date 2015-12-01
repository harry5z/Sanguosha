package ui.game.interfaces;

import core.heroes.Hero;

public interface HeroUI<T extends Hero> extends Activatable {

	public T getHero();

	public void setHero(T hero);

}
