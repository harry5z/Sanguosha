package core.heroes.skills;

import core.event.handlers.hero.ZhugeliangTaichiDodgeArbitrationEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;

@SuppressWarnings("serial")
public class ZhugeliangTaichiOriginalHeroSkill extends AbstractPassiveSkill {

	@Override
	public String getName() {
		return "Taichi";
	}

	@Override
	public String getDescription() {
		return "When you do not have a shield equipped, you are considered equipped with Taichi Formation";
	}

	@Override
	public void onGameReady(Game game, PlayerCompleteServer player) {
		game.registerEventHandler(new ZhugeliangTaichiDodgeArbitrationEventHandler(player));
	}

}
