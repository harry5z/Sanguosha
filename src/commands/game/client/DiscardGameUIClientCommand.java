package commands.game.client;

import core.client.game.operations.Operation;
import core.client.game.operations.machanics.DiscardOperation;
import core.player.PlayerInfo;

public class DiscardGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final int amount;
	
	public DiscardGameUIClientCommand(PlayerInfo target, int amount) {
		super(target);
		this.amount = amount;
	}
	
	@Override
	protected Operation getOperation() {
		return new DiscardOperation(this.amount);
	}

}
