package core.heroes.skills;

import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import core.client.game.listener.SeeThroughSkillClientEventListener;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import ui.game.interfaces.SkillUI;

@SuppressWarnings("serial")
public class ZhugeliangSeeThroughHeroSkill implements ActiveSkill {

	@Override
	public void onGameReady(Game game, PlayerCompleteServer player) {

	}

	@Override
	public String getName() {
		return "See Through";
	}

	@Override
	public String getDescription() {
		return "You may use any BLACK card on hand as Neutralization";
	}

	@Override
	public ClientEventListener<? extends ClientGameEvent> getClientEventListener(SkillUI ui) {
		return new SeeThroughSkillClientEventListener(ui);
	}

}
