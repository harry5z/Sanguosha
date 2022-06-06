package core.heroes.skills;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.ReconsiderationSkillInGameServerCommand;
import core.client.GamePanel;
import core.client.game.listener.skills.ReconsiderationSkillDealEventListener;
import core.player.PlayerCompleteServer;
import core.player.PlayerState;
import core.server.game.GameInternal;
import ui.game.interfaces.SkillUI;

public class SunQuanReconsiderationHeroSkill extends AbstractDealPhaseActiveSkill {

	private static final long serialVersionUID = 1L;

	@Override
	public void onGameReady(GameInternal game, PlayerCompleteServer player) {
		player.registerDefaultPlayerState(PlayerState.SUN_QUAN_RECONSIDERATION_COUNTER, 0);
	}

	@Override
	public String getName() {
		return "Reconsideration";
	}

	@Override
	public String getDescription() {
		return "In your turn, for once, you may discard any number of cards and draw the same amount";
	}

	@Override
	public void onClientSkillLoaded(GamePanel panel, SkillUI skill) {
		panel.registerEventListener(new ReconsiderationSkillDealEventListener(skill));
	}

	@Override
	public void onClientSkillUnloaded(GamePanel panel, SkillUI skill) {
		panel.removeEventListener(new ReconsiderationSkillDealEventListener(skill));
	}

	@Override
	protected Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return ReconsiderationSkillInGameServerCommand.class;
	}

}
