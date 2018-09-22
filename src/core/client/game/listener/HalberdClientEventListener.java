package core.client.game.listener;

import core.client.GamePanel;
import core.client.game.event.InitiateAttackClientGameEvent;
import core.heroes.Hero;

public class HalberdClientEventListener extends AbstractStatelessClientEventListener<InitiateAttackClientGameEvent> {

	@Override
	public Class<InitiateAttackClientGameEvent> getEventClass() {
		return InitiateAttackClientGameEvent.class;
	}

	@Override
	public void handle(InitiateAttackClientGameEvent event, GamePanel<Hero> panel) {
		if (panel.getContent().getSelf().getHandCount() == 1) {
			event.operation.addMaxTargets(2);
		}
	}

}
