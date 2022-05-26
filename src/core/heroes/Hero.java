package core.heroes;

import java.io.Serializable;
import java.util.List;

import core.heroes.skills.Skill;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;

public interface Hero extends Serializable {
	/**
	 * Four factions of heroes
	 * 
	 * @author Harry
	 */
	public enum Faction {
		WEI, SHU, WU, QUN;
	}

	public enum Gender {
		MALE, FEMALE;
	}

	public String getName();

	public int getHealthLimit();

	public Gender getGender();

	public Faction getForce();

	public List<? extends Skill> getSkills();

	public void onGameReady(GameInternal game, PlayerCompleteServer player);

}
