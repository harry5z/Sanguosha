package commands.game.client;
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

}
