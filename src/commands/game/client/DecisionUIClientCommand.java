package commands.game.client;

import core.client.game.operations.Operation;
import core.client.game.operations.mechanics.DecisionOperation;
import core.player.PlayerInfo;

public class DecisionUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String messsage;
	
	public DecisionUIClientCommand(PlayerInfo target, String message) {
		super(target);
		this.messsage = message;
	}

	@Override
	protected Operation getOperation() {
		return new DecisionOperation(this.messsage);
	}

}
