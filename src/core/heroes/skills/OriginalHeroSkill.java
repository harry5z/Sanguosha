package core.heroes.skills;

import core.player.PlayerCompleteServer;
import core.server.game.Game;

public interface OriginalHeroSkill extends Skill {
	
	public void onGameReady(Game game, PlayerCompleteServer player);

}
