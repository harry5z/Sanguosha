package core.client.game.listener;

import core.client.GamePanel;
import core.client.game.event.InitiateAttackClientGameEvent;

public class HalberdClientEventListener extends AbstractStatelessClientEventListener<InitiateAttackClientGameEvent> {

	@Override
	public Class<InitiateAttackClientGameEvent> getEventClass() {
		return InitiateAttackClientGameEvent.class;
	}

	@Override
	public void handle(InitiateAttackClientGameEvent event, GamePanel panel) {
		if (panel.getGameState().getSelf().getHandCount() == 1) {
			event.operation.addMaxTargets(2);
		}
	}

}
