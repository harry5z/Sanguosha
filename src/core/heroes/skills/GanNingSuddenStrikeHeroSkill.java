package core.heroes.skills;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.SuddenStrikeSkillInGameServerCommand;
import core.client.GamePanel;
import core.client.game.listener.skills.SuddenStrikeSkillDealEventListener;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import ui.game.interfaces.SkillUI;

public class GanNingSuddenStrikeHeroSkill extends AbstractDealPhaseActiveSkill {

	private static final long serialVersionUID = 1L;

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

	@Override
	protected Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return SuddenStrikeSkillInGameServerCommand.class;
	}

}
