package core.heroes.skills;

import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import core.client.game.listener.FireAttackSkillDealEventListener;
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
		return "In your turn, you can use any RED card as Fire Attack";
	}

	@Override
	public ClientEventListener<? extends ClientGameEvent> getClientEventListener(SkillUI ui) {
		return new FireAttackSkillDealEventListener(ui);
	}

}
