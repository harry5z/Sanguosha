package core.event.handlers.turn;

import java.util.stream.Collectors;

import commands.game.client.DiscardGameUIClientCommand;
import core.event.game.turn.DiscardTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DiscardTurnEventHandler extends AbstractEventHandler<DiscardTurnEvent> {

	public DiscardTurnEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<DiscardTurnEvent> getEventClass() {
		return DiscardTurnEvent.class;
	}

	@Override
	protected void handleIfActivated(DiscardTurnEvent event, Game game) throws GameFlowInterruptedException {
		if (!this.player.equals(game.getCurrentPlayer())) {
			return;
		}
		int amount = this.player.getHandCount() - this.player.getCardOnHandLimit();
		if (amount > 0) {
			game.pushGameController(new AbstractSingleStageGameController(game) {
				@Override
				protected void handleOnce() throws GameFlowInterruptedException {
					game.getConnectionController().sendCommandToPlayers(
						game.getPlayersInfo().stream().collect(
							Collectors.toMap(
								info -> info.getName(), 
								info -> new DiscardGameUIClientCommand(game.getCurrentPlayer().getPlayerInfo(), amount)
							)
						)
					);
					throw new GameFlowInterruptedException();
				}
			});
		}
	}

}
