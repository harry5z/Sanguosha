package core.client.game.listener;

import core.client.GamePanel;
import core.client.game.event.ClientGameEvent;
import core.heroes.Hero;

public abstract class AbstractStatelessClientEventListener<T extends ClientGameEvent> extends AbstractClientEventListener<T> {

	@Override
	public void onDeactivated(GamePanel<Hero> panel) {}

}
