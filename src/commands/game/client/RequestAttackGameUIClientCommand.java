package commands.game.client;

import core.client.game.operations.Operation;
import core.client.game.operations.basics.AttackReactionOperation;
import core.player.PlayerInfo;

public class RequestAttackGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	
	public RequestAttackGameUIClientCommand(PlayerInfo target, String message) {
		super(target);
		this.message = message;
	}

	@Override
	protected Operation getOperation() {
		return new AttackReactionOperation(this.message);
	}

}
