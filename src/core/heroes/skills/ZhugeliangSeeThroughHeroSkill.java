package core.heroes.skills;

import core.client.GamePanel;
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
	public void onClientSkillLoaded(GamePanel panel, SkillUI skill) {
		panel.registerEventListener(new SeeThroughSkillClientEventListener(skill));
	}

	@Override
	public void onClientSkillUnloaded(GamePanel panel, SkillUI skill) {
		panel.removeEventListener(new SeeThroughSkillClientEventListener(skill));
	}

}
