package core.heroes.original;

import java.util.ArrayList;
import java.util.List;

import core.heroes.Hero;
import core.heroes.skills.HeroSkill;
import core.heroes.skills.Skill;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;

/**
 * Hero class, consisting of all heroes. Hero is effectively final.
 * 
 * @author Harry
 *
 */
public abstract class AbstractHero implements Hero {

	private static final long serialVersionUID = 1L;

	/**
	 * Unique name for each hero
	 */
	private final String name;

	/**
	 * Wei/Shu/Wu/Qun
	 */
	private final Faction faction;

	/**
	 * Male/Female
	 */
	private final Gender gender;

	/**
	 * Usually 3 or 4
	 */
	private final int healthLimit;

	/**
	 * Hero's set of skills
	 */
	private final List<HeroSkill> skills;

	/**
	 * Construct a hero with given health limit, force, gender, name, and a set of
	 * skills
	 * 
	 * @param healthLimit : hero's health limit
	 * @param force       : hero's force
	 * @param gender      : hero's gender
	 * @param name        : hero's name
	 * @param skills      : hero's skills
	 */
	public AbstractHero(int healthLimit, Faction force, Gender gender, String name, HeroSkill... skills) {
		this.name = name;
		this.faction = force;
		this.gender = gender;
		this.healthLimit = healthLimit;
		this.skills = new ArrayList<>();
		for (HeroSkill skill : skills)
			this.skills.add(skill);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getHealthLimit() {
		return healthLimit;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public Faction getForce() {
		return faction;
	}

	@Override
	public List<? extends Skill> getSkills() {
		return skills;
	}

	@Override
	public void onGameReady(GameInternal game, PlayerCompleteServer player) {
		for (HeroSkill skill : this.skills) {
			skill.onGameReady(game, player);
		}
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof AbstractHero ? name.equals(((AbstractHero) obj).name) : false;
	}
}
