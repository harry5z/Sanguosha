package core.event.handlers.instant;

import commands.game.client.ShowCardSelectionPanelUIClientCommand;
import core.event.game.instants.PlayerCardSelectionEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class PlayerCardSelectionEventHandler extends AbstractEventHandler<PlayerCardSelectionEvent> {

	public PlayerCardSelectionEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<PlayerCardSelectionEvent> getEventClass() {
		return PlayerCardSelectionEvent.class;
	}

	@Override
	protected void handleIfActivated(PlayerCardSelectionEvent event, Game game, ConnectionController connection)
		throws GameFlowInterruptedException {
		connection.sendCommandToPlayer(this.player.getName(), new ShowCardSelectionPanelUIClientCommand(event.getSource(), event.getTarget(), event.getZones()));
	}

}
