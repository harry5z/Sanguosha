package core.client.game.listener;

import core.client.GamePanel;
import core.client.game.event.DealClientGameEvent;
import core.client.game.operations.skills.SuddenStrikeSkillOperation;
import ui.game.interfaces.SkillUI;

public class SuddenStrikeSkillDealEventListener extends AbstractClientEventListener<DealClientGameEvent> {

	private SkillUI skill;
	
	public SuddenStrikeSkillDealEventListener(SkillUI skill) {
		this.skill = skill;
	}
	
	@Override
	public Class<DealClientGameEvent> getEventClass() {
		return DealClientGameEvent.class;
	}

	@Override
	public void handle(DealClientGameEvent event, GamePanel panel) {
		if (event.isStart()) {
			this.skill.setActionOnActivation(() -> {
				panel.pushOperation(new SuddenStrikeSkillOperation(this.skill));
			});
			this.skill.setActivatable(true);
		} else {
			this.onDeactivated(panel);
		}
		
	}

	@Override
	public void onDeactivated(GamePanel panel) {
		this.skill.setActivatable(false);
	}
}
