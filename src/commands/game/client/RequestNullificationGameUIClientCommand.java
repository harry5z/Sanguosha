package commands.game.client;

import java.util.UUID;

import core.client.GamePanel;
import core.client.game.operations.instants.NullificationOperation;

public class RequestNullificationGameUIClientCommand extends AbstractGameUIClientCommand {

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

}
