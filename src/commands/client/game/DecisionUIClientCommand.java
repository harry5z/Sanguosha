package commands.client.game;

import java.util.Set;

import commands.server.ingame.DecisionInGameServerCommand;
import commands.server.ingame.InGameServerCommand;
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

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		return Set.of(DecisionInGameServerCommand.class);
	}

	@Override
	public InGameServerCommand getDefaultResponse() {
		return new DecisionInGameServerCommand(false);
	}

	@Override
	protected String getMessageForOthers() {
		return "Waiting on " + target.getName() + " to make a decision";
	}

}
