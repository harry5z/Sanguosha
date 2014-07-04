package heroes.total_war;

import heroes.Hero;
import heroes.original.HeroOriginal;

import java.awt.Image;
import java.util.HashSet;
import java.util.Set;

import skills.Skill;

public class HeroTotalWar implements Hero
{
	private final HeroComponent primary;
	private final HeroComponent secondary;
	private final Force force;
	private static class HeroComponent
	{
		final HeroOriginal hero;
		private boolean revealed;
		HeroComponent(HeroOriginal hero)
		{
			this.hero = hero;
			this.revealed = false;
		}
		boolean isRevealed()
		{
			return revealed;
		}
		void reveal()
		{
			revealed = true;
		}
		void conceal()
		{
			revealed = false;
		}
	}
	/**
	 * A hero in the "Total War" mode consists of two normal heroes of the
	 * same force, with their skills combined.
	 * 
	 * @param primary
	 * @param secondary
	 */
	public HeroTotalWar(HeroOriginal primary, HeroOriginal secondary)
	{
		this.primary = new HeroComponent(primary);
		this.secondary = new HeroComponent(secondary);
		this.force = primary.getForce();
	}
	@Override
	public String getName() 
	{
		if(primary.isRevealed() && secondary.isRevealed())
			return primary.hero.getName() + " and " + secondary.hero.getName();
		else if(primary.isRevealed())
			return primary.hero.getName();
		else if(secondary.isRevealed())
			return secondary.hero.getName();
		else
			return "";
	}

	/*
	 * (non-Javadoc)
	 * will not be used
	 * @see heroes.Hero#changeHealthLimitTo(int)
	 */
	@Override
	public void changeHealthLimitTo(int n) {}

	/*
	 * (non-Javadoc)
	 * will not be used
	 * @see heroes.Hero#changeHealthLimitBy(int)
	 */
	@Override
	public void changeHealthLimitBy(int n) {}

	@Override
	public int getHealthLimit() 
	{
		return (primary.hero.getHealthLimit() + secondary.hero.getHealthLimit()) / 2;
	}

	@Override
	public void changeCardLimitTo(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeCardLimitBy(int n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCardOnHandLimit() 
	{
		if(primary.revealed && secondary.revealed)
			return Math.max(primary.hero.getCardOnHandLimit(), secondary.hero.getCardOnHandLimit());
		return 0;
	}

	@Override
	public Gender getGender() 
	{
		if(primary.isRevealed())
			return primary.hero.getGender();
		else if(secondary.isRevealed())
			return secondary.hero.getGender();
		else
			return Gender.GENDERLESS;
	}

	@Override
	public Force getForce()
	{
		return primary.isRevealed() || secondary.isRevealed() ? force : Force.FORCELESS;
	}

	@Override
	public Set<Skill> getSkills() 
	{
		Set<Skill> skills = new HashSet<Skill>();
		if(primary.isRevealed())
			skills.addAll(primary.hero.getSkills());
		if(secondary.isRevealed())
			skills.addAll(secondary.hero.getSkills());
		return skills;
	}

	/**
	 * Check whether the combined hero has ".5" extra health point
	 * @return true in the case of, for example, 3 and 4, false otherwise
	 */
	public boolean hasHealthExtra()
	{
		return (primary.hero.getHealthLimit() + secondary.hero.getHealthLimit()) % 2 == 1;
	}
	public void revealPrimary()
	{
		primary.reveal();
	}
	public void revealSecondary()
	{
		secondary.reveal();
	}
	public void concealPrimary()
	{
		primary.conceal();
	}
	public void concealSecondary()
	{
		secondary.conceal();
	}
	@Override
	public Image getHeroImage() {
		return null;
	}

	@Override
	public Image getCardImage() {
		return null;
	}

}
