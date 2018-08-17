package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.instants.NeutralizationOperation;
import core.heroes.Hero;

public class RequestNeutralizationGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1979788663683644681L;
	
	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		panel.pushOperation(new NeutralizationOperation());
	}

}
