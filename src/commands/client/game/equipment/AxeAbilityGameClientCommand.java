package commands.client.game.equipment;

import java.util.HashMap;
import java.util.Set;

import commands.client.game.AbstractSingleTargetOperationGameClientCommand;
import commands.server.ingame.AxeReactionInGameServerCommand;
import commands.server.ingame.InGameServerCommand;
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

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		return Set.of(AxeReactionInGameServerCommand.class);
	}

	@Override
	public InGameServerCommand getDefaultResponse() {
		return new AxeReactionInGameServerCommand(new HashMap<>());
	}
	
	@Override
	protected String getMessageForOthers() {
		return "Waiting on " + target.getName() + " to decide wheter to use Axe";
	}

}
