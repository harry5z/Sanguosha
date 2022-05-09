package core.heroes.skills;

import core.client.GamePanel;
import core.client.game.listener.skills.SuddenStrikeSkillDealEventListener;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import ui.game.interfaces.SkillUI;

public class GanNingSuddenStrikeHeroSkill implements ActiveSkill {

	@Override
	public void onGameReady(Game game, PlayerCompleteServer player) {
		
	}

	@Override
	public String getName() {
		return "Sudden Strike";
	}

	@Override
	public String getDescription() {
		return "In your turn, you may use any BLACK card as Sabotage";
	}

	@Override
	public void onClientSkillLoaded(GamePanel panel, SkillUI skill) {
		panel.registerEventListener(new SuddenStrikeSkillDealEventListener(skill));
	}

	@Override
	public void onClientSkillUnloaded(GamePanel panel, SkillUI skill) {
		panel.removeEventListener(new SuddenStrikeSkillDealEventListener(skill));
	}

}
