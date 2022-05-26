package core.heroes.skills;

import core.event.handlers.hero.ZhugeliangTaichiDodgeEquipmentCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;

public class ZhugeliangTaichiOriginalHeroSkill extends AbstractPassiveSkill {

	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Taichi";
	}

	@Override
	public String getDescription() {
		return "When you do not have a shield equipped, you are considered equipped with Taichi Formation";
	}

	@Override
	public void onGameReady(GameInternal game, PlayerCompleteServer player) {
		game.registerEventHandler(new ZhugeliangTaichiDodgeEquipmentCheckEventHandler(player));
	}

}
