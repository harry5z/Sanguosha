package core.event.handlers.turn;

import java.util.stream.Collectors;

import commands.game.client.DiscardGameUIClientCommand;
import core.event.game.turn.DiscardTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import core.server.game.GameInternal;
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
	protected void handleIfActivated(DiscardTurnEvent event, GameDriver game) throws GameFlowInterruptedException {
		if (!this.player.equals(event.currentPlayer)) {
			return;
		}
		int amount = this.player.getHandCount() - this.player.getCardOnHandLimit();
		if (amount > 0) {
			game.pushGameController(new AbstractSingleStageGameController() {
				@Override
				protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
					game.getConnectionController().sendCommandToPlayers(
						game.getPlayers().stream().collect(
							Collectors.toMap(
								p -> p.getName(), 
								p -> new DiscardGameUIClientCommand(game.getCurrentPlayer().getPlayerInfo(), amount)
							)
						)
					);
					throw new GameFlowInterruptedException();
				}
			});
		}
	}

}
