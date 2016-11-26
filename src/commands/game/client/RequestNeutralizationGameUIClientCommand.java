package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.instants.NeutralizationOperation;
import core.heroes.Hero;

public class RequestNeutralizationGameUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = 1979788663683644681L;
	
	
	public RequestNeutralizationGameUIClientCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		panel.pushOperation(new NeutralizationOperation());
	}

}
