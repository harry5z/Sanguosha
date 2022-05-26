package core.heroes.skills;

import core.event.handlers.hero.HuangZhongSharpshooterAttackCheckEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;

public class HuangZhongSharpshooterHeroSkill extends AbstractPassiveSkill {

	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Sharpshooter";
	}

	@Override
	public String getDescription() {
		return "Your Attack cannot be Dodged if (1) target's hand count >= your current HP, or (2) target's hand count <= your attack range";
	}

	@Override
	public void onGameReady(GameInternal game, PlayerCompleteServer player) {
		game.registerEventHandler(new HuangZhongSharpshooterAttackCheckEventHandler(player));
	}

}
