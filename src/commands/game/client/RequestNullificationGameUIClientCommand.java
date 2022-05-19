package commands.game.client;

import java.util.Set;
import java.util.UUID;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.NullificationReactionInGameServerCommand;
import commands.game.server.ingame.NullificationTimeoutInGameServerCommand;
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
		panel.pushPlayerActionOperation(new NullificationOperation(this.message), timeoutMS);
		panel.getGameUI().getOtherPlayersUI().forEach(ui -> ui.showCountdownBar(timeoutMS));
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

	@Override
	public InGameServerCommand getDefaultResponse() {
		// Note that Nullification Timeout response is not an allowed response type.
		// This can only be used by the server internally
		return new NullificationTimeoutInGameServerCommand();
	}

}
