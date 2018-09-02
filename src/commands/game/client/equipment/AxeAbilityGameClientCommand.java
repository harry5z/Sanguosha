package commands.game.client.equipment;

import commands.game.client.AbstractSingleTargetOperationGameClientCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.equipment.AxeOperation;
import core.player.PlayerInfo;

public class AxeAbilityGameClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;

	public AxeAbilityGameClientCommand(PlayerInfo target) {
		super(target);
	}

	@Override
	protected Operation getOperation() {
		return new AxeOperation();
	}

}
