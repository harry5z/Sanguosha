package core.heroes.skills;

import commands.server.ingame.ArrowSalvoSkillIngameServerCommand;
import commands.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.client.game.listener.skills.ArrowSalvoSkillDealEventListener;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import ui.game.interfaces.SkillUI;

public class YuanShaoArrowSalvoHeroSkill extends AbstractDealPhaseActiveSkill {

	private static final long serialVersionUID = 1L;

	@Override
	public void onGameReady(GameInternal game, PlayerCompleteServer player) {

	}

	@Override
	public String getName() {
		return "Arrow Salvo";
	}

	@Override
	public String getDescription() {
		return "In your turn, you may use 2 cards on hand of the same suit as Arrow Salvo";
	}

	@Override
	public void onClientSkillLoaded(GamePanel panel, SkillUI skill) {
		panel.registerEventListener(new ArrowSalvoSkillDealEventListener(skill));
	}

	@Override
	public void onClientSkillUnloaded(GamePanel panel, SkillUI skill) {
		panel.removeEventListener(new ArrowSalvoSkillDealEventListener(skill));
	}

	@Override
	protected Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return ArrowSalvoSkillIngameServerCommand.class;
	}

}
