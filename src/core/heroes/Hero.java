package core.heroes;

import java.io.Serializable;
import java.util.List;

import core.heroes.skills.Skill;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

public interface Hero extends Serializable
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

	public List<? extends Skill> getSkills();
	
	public void onGameReady(Game game, PlayerCompleteServer player);

}
