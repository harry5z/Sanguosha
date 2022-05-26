package core.heroes;

import java.util.ArrayList;
import java.util.List;

import core.heroes.original.GanNing;
import core.heroes.original.HuangZhong;
import core.heroes.original.SunQuan;
import core.heroes.original.WeiYan;
import core.heroes.original.YuJin;
import core.heroes.original.YuanShao;
import core.heroes.original.ZhugeliangRestingDragon;
import core.server.game.Game;

/**
 * This class is mainly used by a {@link Game}
 * are allowed.
 * 
 */
public class HeroPool {
	
	private static final HeroPool INSTANCE = new HeroPool();
	
	private final List<Hero> heroes;
	private final List<Hero> emperorHeroes;
	private final List<Hero> nonEmperorHeroes;

	private HeroPool() {
		this.emperorHeroes = List.of(
			new SunQuan(),
			new YuanShao()
		);
		this.nonEmperorHeroes = List.of(
			new GanNing(),
			new YuJin(),
			new WeiYan(),
			new HuangZhong(),
			new ZhugeliangRestingDragon()
		);
		this.heroes = new ArrayList<>();
		this.heroes.addAll(emperorHeroes);
		this.heroes.addAll(nonEmperorHeroes);
	}
	
	/**
	 * Get a list of heroes that are considered emperor heroes
	 * @return an unmodifiable list of emperor heroes
	 */
	public static List<Hero> getEmperorHeroes() {
		return List.copyOf(INSTANCE.emperorHeroes);
	}
	
	/**
	 * Get a list of heroes that are not considered emperor heroes
	 * @return an unmodifiable list of non-emperor heroes
	 */
	public static List<Hero> getNonEmperorHeroes() {
		return List.copyOf(INSTANCE.nonEmperorHeroes);
	}

	/**
	 * Get a list of all heroes
	 * @return an unmodifiable list of all heroes
	 */
	public static List<Hero> getAllHeroes() {
		return List.copyOf(INSTANCE.heroes);
	}

}
