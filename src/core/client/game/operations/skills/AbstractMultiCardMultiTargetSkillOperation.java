package core.client.game.operations.skills;

import core.client.game.operations.AbstractMultiCardMultiTargetOperation;
import ui.game.interfaces.SkillUI;

public abstract class AbstractMultiCardMultiTargetSkillOperation extends AbstractMultiCardMultiTargetOperation {

	private final SkillUI skill;
	
	public AbstractMultiCardMultiTargetSkillOperation(SkillUI skill, int maxCards, int maxTargets) {
		super(maxCards, maxTargets);
		this.skill = skill;
	}
	
	@Override
	protected final void onLoadedCustom() {
		this.skill.setActivatable(true);
		this.skill.setActivated(true);
		this.skill.setActionOnActivation(() -> {
			// Behave as if the CANCEL button is pressed
			this.onCanceled();
		});
	}
	
	@Override
	protected final void onUnloadedCustom() {
		this.skill.setActivatable(false);
		this.skill.setActivated(false);
	}

}
