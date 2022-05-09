package core.client.game.listener.equipment;

import core.client.GamePanel;
import core.client.game.event.InitiateAttackClientGameEvent;
import core.client.game.listener.AbstractStatelessClientEventListener;

public class HalberdClientEventListener extends AbstractStatelessClientEventListener<InitiateAttackClientGameEvent> {

	@Override
	public Class<InitiateAttackClientGameEvent> getEventClass() {
		return InitiateAttackClientGameEvent.class;
	}

	@Override
	public void handleOnLoaded(InitiateAttackClientGameEvent event, GamePanel panel) {
		if (panel.getGameState().getSelf().getHandCount() == 1) {
			event.operation.addMaxTargets(2);
		}
	}

}
