package core.heroes.skills;

import core.client.GamePanel;
import core.client.game.listener.skills.FireAttackSkillDealEventListener;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import ui.game.interfaces.SkillUI;

@SuppressWarnings("serial")
public class ZhugeliangFireAttackOriginalHeroSkill implements ActiveSkill {

	@Override
	public void onGameReady(Game game, PlayerCompleteServer player) {

	}

	@Override
	public String getName() {
		return "Fire Attack";
	}

	@Override
	public String getDescription() {
		return "In your turn, you can use any RED card on hand as Fire Attack";
	}

	@Override
	public void onClientSkillLoaded(GamePanel panel, SkillUI skill) {
		panel.registerEventListener(new FireAttackSkillDealEventListener(skill));
	}

	@Override
	public void onClientSkillUnloaded(GamePanel panel, SkillUI skill) {
		panel.removeEventListener(new FireAttackSkillDealEventListener(skill));
	}

}
