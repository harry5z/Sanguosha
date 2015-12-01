package core.heroes.original;

import java.util.HashSet;
import java.util.Set;

import core.heroes.Hero;
import core.heroes.skills.Skill;

/**
 * Hero class, consisting of all heroes
 * @author Harry
 *
 */
public abstract class HeroOriginal implements Hero
{
	/**
	 * Unique name for each hero
	 */
	private final String name;
	
	/**
	 * Wei/Shu/Wu/Qun
	 */
	private final Force force;
	
	/**
	 * Male/Female
	 */
	private final Gender gender;
	
	/**
	 * Usually 3 or 4
	 */
	private int healthLimit;
	
	/**
	 * Usually equal to current health, though some skills may affect it
	 */
	private int cardsOnHandLimit;
	
	/**
	 * Hero's set of skills
	 */
	private final Set<Skill> skills;
	
	/**
	 * Construct a hero with given health limit, force, gender, name, and a set of skills
	 * @param healthLimit : hero's health limit
	 * @param force : hero's force
	 * @param gender : hero's gender
	 * @param name : hero's name
	 * @param skills : hero's skills
	 */
	public HeroOriginal(int healthLimit, Force force, Gender gender, String name, Skill... skills)
	{
		this.name = name;
		this.force = force;
		this.gender = gender;
		this.healthLimit = healthLimit;
		this.cardsOnHandLimit = healthLimit;
		this.skills = new HashSet<Skill>();
		for(Skill skill : skills)
			this.skills.add(skill);
	}
	@Override
	public String getName()
	{
		return name;
	}
	@Override
	public void changeHealthLimitTo(int n)
	{
		healthLimit = n;
	}
	@Override
	public void changeHealthLimitBy(int n)
	{
		healthLimit += n;
	}
	@Override
	public int getHealthLimit()
	{
		return healthLimit;
	}
	@Override
	public void changeCardLimitTo(int n)
	{
		cardsOnHandLimit = n;
	}
	@Override
	public void changeCardLimitBy(int n)
	{
		cardsOnHandLimit += n;
	}
	@Override
	public int getCardOnHandLimit()
	{
		return cardsOnHandLimit;
	}
	@Override
	public Gender getGender()
	{
		return gender;
	}
	@Override
	public Force getForce()
	{
		return force;
	}
	@Override
	public Set<Skill> getSkills()
	{
		return skills;
	}
}
