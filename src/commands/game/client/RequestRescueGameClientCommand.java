package commands.game.client;

import java.util.Set;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.RescueReactionInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.basics.RescueReactionOperation;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;

public class RequestRescueGameClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	private final String message;
	private final PlayerInfo dyingPlayer;

	public RequestRescueGameClientCommand(PlayerCompleteServer rescuer, PlayerCompleteServer dyingPlayer) {
		super(rescuer.getPlayerInfo());
		this.dyingPlayer = dyingPlayer.getPlayerInfo();
		int amount = 1 - dyingPlayer.getHealthCurrent();
		if (rescuer == dyingPlayer) {
			message = "You are dying, use " + amount + " Wine or Peach to save yourself?";
		} else {
			message = "Player '" + dyingPlayer.getName() + " is dying. Use " + amount + " Peaches to save them?";
		}
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		// TODO query for viable skill response types
		return Set.of(RescueReactionInGameServerCommand.class);
	}

	@Override
	public InGameServerCommand getDefaultResponse() {
		return new RescueReactionInGameServerCommand(null);
	}

	@Override
	protected Operation getOperation() {
		return new RescueReactionOperation(dyingPlayer, message);
	}
	
	@Override
	protected String getMessageForOthers() {
		return "Waiting on " + target.getName() + " to decide whether to save " + dyingPlayer.getName();
	}

}
