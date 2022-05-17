package commands.game.client.sync;

import java.util.Set;
import java.util.UUID;

import commands.game.client.AbstractGameUIClientCommand;
import commands.game.server.ingame.InGameServerCommand;

public abstract class AbstractSyncGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;

	@Override
	public UUID generateResponseID(String name) {
		// Sync commands do not allow responses
		return null;
	}
	
	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		return null;
	}

}
