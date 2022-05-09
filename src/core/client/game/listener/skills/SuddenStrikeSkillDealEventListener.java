package core.client.game.listener.skills;

import core.client.GamePanel;
import core.client.game.event.DealClientGameEvent;
import core.client.game.operations.skills.SuddenStrikeSkillOperation;
import ui.game.interfaces.SkillUI;

public class SuddenStrikeSkillDealEventListener extends AbstractActiveSkillClientEventListener<DealClientGameEvent> {

	public SuddenStrikeSkillDealEventListener(SkillUI skill) {
		super(skill);
	}
	
	@Override
	public Class<DealClientGameEvent> getEventClass() {
		return DealClientGameEvent.class;
	}

	@Override
	public void handleOnLoaded(DealClientGameEvent event, GamePanel panel) {
		this.skill.setActionOnActivation(() -> {
			panel.pushOperation(new SuddenStrikeSkillOperation(this.skill));
		});
		this.skill.setActivatable(true);
	}

}
