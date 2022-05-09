package commands.game.client;

import core.client.game.operations.Operation;
import core.client.game.operations.mechanics.DealOperation;
import core.player.PlayerInfo;

public class DealStartGameUIClientCommmand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	public DealStartGameUIClientCommmand(PlayerInfo target) {
		super(target);
	}

	@Override
	protected Operation getOperation() {
		return new DealOperation();
	}

}
