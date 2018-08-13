package core.event.handlers.equipment;

import cards.Card.Color;
import core.event.game.basic.AttackEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.AttackGameController.AttackStage;
import exceptions.server.game.GameFlowInterruptedException;

public class IronShieldAttackEventHandler extends AbstractEventHandler<AttackEvent> {

	public IronShieldAttackEventHandler(PlayerCompleteServer player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<AttackEvent> getEventClass() {
		return AttackEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.getPlayerInfo().equals(event.getTarget())) {
			return;
		}
		
		// block BLACK attacks only
		if (event.getAttackCard().getColor() != Color.BLACK) {
			return;
		}
		
		throw new GameFlowInterruptedException(() -> {
			event.getController().setStage(AttackStage.END);
			event.getController().proceed();
		});
	}

}
