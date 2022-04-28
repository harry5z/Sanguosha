package core.heroes.skills;

import core.event.handlers.hero.YujinAttackCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

/**
 * One of the easiest skill to implement. (Original) Yujin's skill is simply that BLACK attacks
 * do not affect Yujin, though Yujin can still be targeted.
 * 
 * @author Harry
 *
 */
@SuppressWarnings("serial")
public class YujinOriginalHeroSkill extends AbstractPassiveSkill {

	@Override
	public String getName() {
		return "Defense";
	}

	@Override
	public String getDescription() {
		return "If you are the target of a BLACK attack card, its effect is nullified";
	}

	@Override
	public void onGameReady(Game game, PlayerCompleteServer owner) {
		game.registerEventHandler(new YujinAttackCheckEventHandler(owner));
	}

}
