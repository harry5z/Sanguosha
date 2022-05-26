package core.heroes.skills;

import core.event.handlers.hero.WeiYanRampageOnDamageEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;

public class WeiYanRampageHeroSkill extends AbstractPassiveSkill {

	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		return "Rampage";
	}

	@Override
	public String getDescription() {
		return "You heal 1HP for each 1 damage you deal to a player within 1 distance of you";
	}

	@Override
	public void onGameReady(GameInternal game, PlayerCompleteServer owner) {
		game.registerEventHandler(new WeiYanRampageOnDamageEventHandler(owner));
	}

}
