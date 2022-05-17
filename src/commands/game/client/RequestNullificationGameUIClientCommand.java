package commands.game.client;

import java.util.Set;
import java.util.UUID;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.NullificationReactionInGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.instants.NullificationOperation;

public class RequestNullificationGameUIClientCommand extends AbstractPlayerActionGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	private UUID uuid;
	
	public RequestNullificationGameUIClientCommand(String message) {
		this.message = message;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		// response ID must be present for the response to be accepted by server
		panel.setNextResponseID(uuid);
		panel.pushOperation(new NullificationOperation(this.message));
	}

	@Override
	public UUID generateResponseID(String name) {
		uuid = UUID.randomUUID(); // anyone can respond to Nullification
		return uuid;
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		// TODO also look for hero skills
		return Set.of(NullificationReactionInGameServerCommand.class);
	}

}
