package core.heroes.skills;

import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;

public interface HeroSkill extends Skill {
	
	public void onGameReady(GameInternal game, PlayerCompleteServer player);

}
