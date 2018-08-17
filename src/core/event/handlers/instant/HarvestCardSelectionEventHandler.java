package core.event.handlers.instant;

import commands.game.client.ShowHarvestCardSelectionPaneUIClientCommand;
import core.event.game.instants.HarvestCardSelectionEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class HarvestCardSelectionEventHandler extends AbstractEventHandler<HarvestCardSelectionEvent> {

	public HarvestCardSelectionEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<HarvestCardSelectionEvent> getEventClass() {
		return HarvestCardSelectionEvent.class;
	}

	@Override
	protected void handleIfActivated(HarvestCardSelectionEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		connection.sendCommandToPlayer(this.player.getName(), new ShowHarvestCardSelectionPaneUIClientCommand(event.getTarget(), event.getSelectableCards()));
	}

}
