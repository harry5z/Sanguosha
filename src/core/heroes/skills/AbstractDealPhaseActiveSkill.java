package core.heroes.skills;

import commands.game.client.DealStartGameUIClientCommmand;
import commands.game.client.PlayerActionGameClientCommand;
import commands.game.server.ingame.InGameServerCommand;

public abstract class AbstractDealPhaseActiveSkill implements ActiveSkill {

	private static final long serialVersionUID = 1L;

	@Override
	public final Class<? extends InGameServerCommand> getAllowedResponseType(PlayerActionGameClientCommand command) {
		if (command.getClass() == DealStartGameUIClientCommmand.class) {
			return getAllowedDealPhaseResponseType();
		}
		return null;
	}
	
	protected abstract Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType();

}
