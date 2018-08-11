package core.event.handlers.equipment;

import core.event.game.basic.AttackEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Damage.Element;
import core.server.game.Game;
import core.server.game.controllers.AttackGameController.AttackStage;
import exceptions.server.game.GameFlowInterruptedException;

public class RattanArmorAttackEventHandler extends AbstractEventHandler<AttackEvent> {

	public RattanArmorAttackEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackEvent> getEventClass() {
		return AttackEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.getPlayerInfo().equals(event.getTargetInfo())) {
			return;
		}
		
		// block NORMAL attacks only
		if (event.getAttackCard().getElement() != Element.NORMAL) {
			return;
		}
		
		throw new GameFlowInterruptedException(() -> {
			event.getController().setStage(AttackStage.END);
			event.getController().proceed();
		});
	}

}
