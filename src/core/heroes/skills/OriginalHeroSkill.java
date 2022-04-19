package core.heroes.skills;

import core.player.PlayerCompleteServer;
import core.server.game.Game;

public abstract class OriginalHeroSkill implements Skill {
	
	public abstract void onGameReady(Game game, PlayerCompleteServer player);

}
