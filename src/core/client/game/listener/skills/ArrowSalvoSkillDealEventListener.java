package core.client.game.listener.skills;

import core.client.GamePanel;
import core.client.game.event.DealClientGameEvent;
import core.client.game.listener.AbstractClientEventListener;
import core.client.game.operations.skills.ArrowSalvoSkillOperation;
import ui.game.interfaces.SkillUI;

public class ArrowSalvoSkillDealEventListener extends AbstractClientEventListener<DealClientGameEvent> {

	private SkillUI skill;
	
	public ArrowSalvoSkillDealEventListener(SkillUI skill) {
		this.skill = skill;
	}
	
	@Override
	public Class<DealClientGameEvent> getEventClass() {
		return DealClientGameEvent.class;
	}

	@Override
	public void handleOnLoaded(DealClientGameEvent event, GamePanel panel) {
		this.skill.setActionOnActivation(() -> {
			panel.pushOperation(new ArrowSalvoSkillOperation(this.skill));
		});
		this.skill.setActivatable(true);
	}

	@Override
	public void handleOnUnloaded(GamePanel panel) {
		this.skill.setActivatable(false);
	}
}
