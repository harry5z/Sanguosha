package core.heroes.skills;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.ZhugeliangInitiateFireAttackInGameServerCommand;
import core.client.GamePanel;
import core.client.game.listener.skills.FireAttackSkillDealEventListener;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import ui.game.interfaces.SkillUI;

public class ZhugeliangFireAttackOriginalHeroSkill extends AbstractDealPhaseActiveSkill {

	private static final long serialVersionUID = 1L;

	@Override
	public void onGameReady(GameInternal game, PlayerCompleteServer player) {

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

	@Override
	protected Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return ZhugeliangInitiateFireAttackInGameServerCommand.class;
	}

}
