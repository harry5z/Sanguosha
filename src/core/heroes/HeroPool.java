package core.heroes;

import java.util.Set;

import core.heroes.original.GanNing;
import core.heroes.original.SunQuan;
import core.heroes.original.YuJin;
import core.heroes.original.ZhugeliangRestingDragon;
import core.server.game.Game;

/**
 * This class is mainly used by a {@link Game}
 * are allowed.
 * 
 */
public class HeroPool {
	
	private static final HeroPool INSTANCE = new HeroPool();
	
	private final Set<Hero> heroes;
	private final Set<Hero> emperorHeroes;

	private HeroPool() {
		this.emperorHeroes = Set.of(
			new SunQuan()
		);
		this.heroes = Set.of(
			new GanNing(),
			new SunQuan(),
			new YuJin(),
			new ZhugeliangRestingDragon()
		);
	}
	
	public static Set<Hero> getEmperorHeroes() {
		return Set.copyOf(INSTANCE.emperorHeroes);
	}

	public static Set<Hero> getAllHeroes() {
		return Set.copyOf(INSTANCE.heroes);
	}

}
