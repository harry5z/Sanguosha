package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.instants.NeutralizationOperation;

public class RequestNeutralizationGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	
	public RequestNeutralizationGameUIClientCommand(String message) {
		this.message = message;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		panel.pushOperation(new NeutralizationOperation(this.message));
	}

}
