package commands.game.client;

import java.util.Set;

import commands.game.server.ingame.DiscardInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.mechanics.DiscardOperation;
import core.player.PlayerInfo;

public class DiscardGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final int amount;

	public DiscardGameUIClientCommand(PlayerInfo target, int amount) {
		super(target);
		this.amount = amount;
	}
	
	@Override
	protected boolean shouldClearGamePanel() {
		return true;
	}

	@Override
	protected Operation getOperation() {
		return new DiscardOperation(amount);
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		return Set.of(DiscardInGameServerCommand.class);
	}
	
}
