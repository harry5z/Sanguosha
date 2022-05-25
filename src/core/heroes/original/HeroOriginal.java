package core.heroes.original;

import java.util.ArrayList;
import java.util.List;

import core.heroes.Hero;
import core.heroes.skills.OriginalHeroSkill;
import core.heroes.skills.Skill;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

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
	private final Faction force;
	
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
	private final List<OriginalHeroSkill> skills;
	
	/**
	 * Construct a hero with given health limit, force, gender, name, and a set of skills
	 * @param healthLimit : hero's health limit
	 * @param force : hero's force
	 * @param gender : hero's gender
	 * @param name : hero's name
	 * @param skills : hero's skills
	 */
	public HeroOriginal(int healthLimit, Faction force, Gender gender, String name, OriginalHeroSkill... skills)
	{
		this.name = name;
		this.force = force;
		this.gender = gender;
		this.healthLimit = healthLimit;
		this.cardsOnHandLimit = healthLimit;
		this.skills = new ArrayList<>();
		for(OriginalHeroSkill skill : skills)
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
	public Faction getForce()
	{
		return force;
	}
	@Override
	public List<? extends Skill> getSkills()
	{
		return skills;
	}
	
	@Override
	public void onGameReady(Game game, PlayerCompleteServer player) {
		for (OriginalHeroSkill skill : this.skills) {
			skill.onGameReady(game, player);
		}
	}
}
