package core;

import heroes.Hero;
import heroes.original.HeroOriginal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is mainly used by a {@link Game} to 
 * specify what packs of heroes are allowed.
 * @author Harry
 *
 */
public class HeroPool 
{
	private Set<Hero> heroes;
	
	public enum HeroPack
	{
		STANDARD, WIND, FIRE, FOREST, MOUNTAIN, EX1, EX2, EX3, SP, LEGENDARY;
	}
	
	public HeroPool(Set<HeroPack> packs)
	{
		this.heroes = new HashSet<Hero>();
		if (packs.contains(HeroPack.STANDARD))
			initStandard();
		if (packs.contains(HeroPack.WIND))
			initWind();
	}
	
	public Set<Hero> getHeroes()
	{
		return Collections.unmodifiableSet(this.heroes);
	}
	private void initStandard() {}
	private void initWind() {}
	private void initFire() {}
	private void initForest() {}
	private void initMountain() {}
	private void initEX1() {}
	private void initEX2() {}
	private void initEX3() {}
	private void initSP() {}
	private void initLegendary() {} 
}
