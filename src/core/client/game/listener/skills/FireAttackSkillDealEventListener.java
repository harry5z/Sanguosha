package core.client.game.listener.skills;

import core.client.GamePanel;
import core.client.game.event.DealClientGameEvent;
import core.client.game.operations.skills.FireAttackSkillOperation;
import ui.game.interfaces.SkillUI;

public class FireAttackSkillDealEventListener extends AbstractActiveSkillClientEventListener<DealClientGameEvent> {

	public FireAttackSkillDealEventListener(SkillUI skill) {
		super(skill);
	}
	
	@Override
	public Class<DealClientGameEvent> getEventClass() {
		return DealClientGameEvent.class;
	}

	@Override
	public void handleOnLoaded(DealClientGameEvent event, GamePanel panel) {
		if (panel.getGameState().getSelf().getHandCount() > 0) {
			this.skill.setActionOnActivation(() -> {
				panel.pushOperation(new FireAttackSkillOperation(this.skill));
			});
			this.skill.setActivatable(true);
		}
	}

}
