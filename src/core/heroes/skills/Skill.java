package core.heroes.skills;

import java.io.Serializable;

import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import ui.game.interfaces.SkillUI;

public interface Skill extends Serializable {

	public String getName();

	public String getDescription();
	
	public ClientEventListener<? extends ClientGameEvent> getClientEventListener(SkillUI ui);
}
