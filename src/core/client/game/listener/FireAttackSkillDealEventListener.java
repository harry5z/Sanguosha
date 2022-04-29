package core.client.game.listener;

import core.client.GamePanel;
import core.client.game.event.DealClientGameEvent;
import core.client.game.operations.skills.FireAttackSkillOperation;
import ui.game.interfaces.SkillUI;

public class FireAttackSkillDealEventListener extends AbstractClientEventListener<DealClientGameEvent> {

	private SkillUI skill;
	
	public FireAttackSkillDealEventListener(SkillUI skill) {
		this.skill = skill;
	}
	
	@Override
	public Class<DealClientGameEvent> getEventClass() {
		return DealClientGameEvent.class;
	}

	@Override
	public void handle(DealClientGameEvent event, GamePanel panel) {
		if (event.isStart()) {
			if (panel.getGameState().getSelf().getHandCount() > 0) {
				this.skill.setActionOnActivation(() -> {
					panel.pushOperation(new FireAttackSkillOperation(this.skill));
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
