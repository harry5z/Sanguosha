package commands.game.client;

import java.util.Set;

import commands.game.server.ingame.DiscardInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.mechanics.DiscardOperation;
import core.player.PlayerCompleteServer;

public class DiscardGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final transient PlayerCompleteServer player;
	private final int amount;

	public DiscardGameUIClientCommand(PlayerCompleteServer target, int amount) {
		super(target.getPlayerInfo());
		this.player = target;
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

	@Override
	public InGameServerCommand getDefaultResponse() {
		// by default, discard the first X cards
		return new DiscardInGameServerCommand(player.getCardsOnHand().subList(0, amount));
	}

	@Override
	protected String getMessageForOthers() {
		return "Waiting on " + target.getName() + " to discard " + amount + " cards";
	}
	
}
