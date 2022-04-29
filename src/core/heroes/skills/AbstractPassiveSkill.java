package core.heroes.skills;

import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import ui.game.interfaces.SkillUI;

@SuppressWarnings("serial")
public abstract class AbstractPassiveSkill implements PassiveSkill {

	@Override
	public ClientEventListener<ClientGameEvent> getClientEventListener(SkillUI ui) {
		// Passive skills do not require player action
		return null;
	}

}
