package core.client.game.listener.skills;

import core.client.GamePanel;
import core.client.game.event.DealClientGameEvent;
import core.client.game.listener.AbstractClientEventListener;
import core.client.game.operations.skills.ReconsiderationSkillOperation;
import core.player.PlayerState;
import ui.game.interfaces.SkillUI;

public class ReconsiderationSkillDealEventListener extends AbstractClientEventListener<DealClientGameEvent> {

	private SkillUI skill;
	
	public ReconsiderationSkillDealEventListener(SkillUI skill) {
		this.skill = skill;
	}
	
	@Override
	public Class<DealClientGameEvent> getEventClass() {
		return DealClientGameEvent.class;
	}

	@Override
	public void handleOnLoaded(DealClientGameEvent event, GamePanel panel) {
		if (panel.getGameState().getSelf().getPlayerState(PlayerState.SUN_QUAN_RECONSIDERATION_COUNTER) > 0) {
			// skip if this skill has been used once
			return;
		}
		this.skill.setActionOnActivation(() -> {
			panel.pushOperation(new ReconsiderationSkillOperation(this.skill));
		});
		this.skill.setActivatable(true);
	}

	@Override
	public void handleOnUnloaded(GamePanel panel) {
		this.skill.setActivatable(false);
	}
}
