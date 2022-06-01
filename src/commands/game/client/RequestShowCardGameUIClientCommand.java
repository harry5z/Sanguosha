package commands.game.client;
import java.util.Set;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.basics.ShowCardReactionOperation;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;

public class RequestShowCardGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final transient PlayerCompleteServer player;
	private final String message;
	
	public RequestShowCardGameUIClientCommand(PlayerCompleteServer target, String message) {
		super(target.getPlayerInfo());
		this.player = target;
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

	@Override
	public InGameServerCommand getDefaultResponse() {
		// by rule, the player must have at least 1 card on hand
		return new PlayerCardSelectionInGameServerCommand(
			player.getCardsOnHand().get(0),
			PlayerCardZone.HAND
		);
	}
	
	@Override
	protected String getMessageForOthers() {
		return "Waiting on " + target.getName() + " to show a card";
	}

}
