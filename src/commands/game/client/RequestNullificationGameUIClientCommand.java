package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.instants.NullificationOperation;

public class RequestNullificationGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	
	public RequestNullificationGameUIClientCommand(String message) {
		this.message = message;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		panel.pushOperation(new NullificationOperation(this.message));
	}

}
