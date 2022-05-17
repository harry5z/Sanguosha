package commands.game.client;
import java.util.Set;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.basics.ShowCardReactionOperation;
import core.player.PlayerInfo;

public class RequestShowCardGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	
	public RequestShowCardGameUIClientCommand(PlayerInfo target, String message) {
		super(target);
		this.message = message;
	}

	@Override
	protected Operation getOperation() {
		return new ShowCardReactionOperation(this.message);
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		return Set.of(PlayerCardSelectionInGameServerCommand.class);
	}

}
