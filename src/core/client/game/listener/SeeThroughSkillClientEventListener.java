package core.client.game.listener;

import core.client.GamePanel;
import core.client.game.event.RequestNeutralizationClientGameEvent;
import core.client.game.operations.skills.SeeThroughSkillOperation;
import ui.game.interfaces.SkillUI;

public class SeeThroughSkillClientEventListener extends AbstractClientEventListener<RequestNeutralizationClientGameEvent> {

	private SkillUI skill;
	
	public SeeThroughSkillClientEventListener(SkillUI skill) {
		this.skill = skill;
	}
	
	@Override
	public Class<RequestNeutralizationClientGameEvent> getEventClass() {
		return RequestNeutralizationClientGameEvent.class;
	}

	@Override
	public void handle(RequestNeutralizationClientGameEvent event, GamePanel panel) {
		if (event.isStart()) {
			if (panel.getGameState().getSelf().getHandCount() > 0) {
				this.skill.setActionOnActivation(() -> {
					panel.pushOperation(new SeeThroughSkillOperation(this.skill));
				});
				this.skill.setActivatable(true);
			}
		} else {
			this.onDeactivated(panel);
		}		
	}

	@Override
	public void onDeactivated(GamePanel panel) {
		this.skill.setActivatable(false);
	}

}
