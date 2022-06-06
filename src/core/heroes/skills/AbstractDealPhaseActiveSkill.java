package core.heroes.skills;

import commands.client.game.DealStartGameUIClientCommmand;
import commands.client.game.PlayerActionGameClientCommand;
import commands.server.ingame.InGameServerCommand;

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
