package core.client.game.listener.skills;

import core.client.GamePanel;
import core.client.game.event.ClientGameEvent;
import core.client.game.listener.AbstractClientEventListener;
import ui.game.interfaces.SkillUI;

public abstract class AbstractActiveSkillClientEventListener<T extends ClientGameEvent> extends AbstractClientEventListener<T> {
	
	protected final SkillUI skill;
	
	public AbstractActiveSkillClientEventListener(SkillUI skill) {
		this.skill = skill;
	}
	
	@Override
	public final void handleOnUnloaded(GamePanel panel) {
		this.skill.setActivatable(false);
	}

}
