package core.client.game.listener.skills;

import core.client.GamePanel;
import core.client.game.event.RequestNeutralizationClientGameEvent;
import core.client.game.operations.skills.SeeThroughSkillOperation;
import ui.game.interfaces.SkillUI;

public class SeeThroughSkillClientEventListener extends AbstractActiveSkillClientEventListener<RequestNeutralizationClientGameEvent> {

	public SeeThroughSkillClientEventListener(SkillUI skill) {
		super(skill);
	}
	
	@Override
	public Class<RequestNeutralizationClientGameEvent> getEventClass() {
		return RequestNeutralizationClientGameEvent.class;
	}

	@Override
	public void handleOnLoaded(RequestNeutralizationClientGameEvent event, GamePanel panel) {
		if (panel.getGameState().getSelf().getHandCount() > 0) {
			this.skill.setActionOnActivation(() -> {
				panel.pushOperation(new SeeThroughSkillOperation(this.skill));
			});
			this.skill.setActivatable(true);
		}
	}

}
