package commands.game.client;

import core.client.game.operations.Operation;
import core.client.game.operations.basics.DodgeReactionOperation;
import core.player.PlayerInfo;

public class RequestDodgeGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {
	
	private static final long serialVersionUID = 1L;

	private final String message;
	
	public RequestDodgeGameUIClientCommand(PlayerInfo target, String message) {
		super(target);
		this.message = message;
	}

	@Override
	protected Operation getOperation() {
		return new DodgeReactionOperation(this.message);
	}

}
