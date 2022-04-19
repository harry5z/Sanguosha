package core.heroes;

import java.util.Set;

import core.heroes.skills.Skill;

public interface Hero 
{
	/**
	 * Four forces of heroes
	 * @author Harry
	 */
	public enum Force
	{
		WEI, SHU, WU, QUN, FORCELESS;
	}
	
	public enum Gender
	{
		MALE, FEMALE, GENDERLESS;
	}

	public String getName();

	public void changeHealthLimitTo(int n);

	public void changeHealthLimitBy(int n);

	public int getHealthLimit();

	public void changeCardLimitTo(int n);

	public void changeCardLimitBy(int n);

	public int getCardOnHandLimit();
	
	public Gender getGender();

	public Force getForce();

	public Set<? extends Skill> getSkills();

}
