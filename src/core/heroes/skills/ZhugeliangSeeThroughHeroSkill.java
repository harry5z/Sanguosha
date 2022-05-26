package core.heroes.skills;

import commands.game.client.PlayerActionGameClientCommand;
import commands.game.client.RequestNullificationGameUIClientCommand;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.ZhugeliangNullificationReactionInGameServerCommand;
import core.client.GamePanel;
import core.client.game.listener.skills.SeeThroughSkillClientEventListener;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import ui.game.interfaces.SkillUI;

public class ZhugeliangSeeThroughHeroSkill implements ActiveSkill {

	private static final long serialVersionUID = 1L;

	@Override
	public void onGameReady(GameInternal game, PlayerCompleteServer player) {

	}

	@Override
	public String getName() {
		return "See Through";
	}

	@Override
	public String getDescription() {
		return "You may use any BLACK card on hand as Nullification";
	}

	@Override
	public void onClientSkillLoaded(GamePanel panel, SkillUI skill) {
		panel.registerEventListener(new SeeThroughSkillClientEventListener(skill));
	}

	@Override
	public void onClientSkillUnloaded(GamePanel panel, SkillUI skill) {
		panel.removeEventListener(new SeeThroughSkillClientEventListener(skill));
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedResponseType(PlayerActionGameClientCommand command) {
		if (command.getClass() == RequestNullificationGameUIClientCommand.class) {
			return ZhugeliangNullificationReactionInGameServerCommand.class;
		}
		return null;
	}

}
