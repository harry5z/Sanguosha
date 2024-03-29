package core.heroes.skills;

import core.event.handlers.hero.YujinAttackCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;

/**
 * One of the easiest skill to implement. (Original) Yujin's skill is simply that BLACK attacks
 * do not affect Yujin, though Yujin can still be targeted.
 * 
 * @author Harry
 *
 */
public class YujinOriginalHeroSkill extends AbstractPassiveSkill {

	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Defense";
	}

	@Override
	public String getDescription() {
		return "If you are the target of a BLACK attack card, its effect is nullified";
	}

	@Override
	public void onGameReady(GameInternal game, PlayerCompleteServer owner) {
		game.registerEventHandler(new YujinAttackCheckEventHandler(owner));
	}

}
