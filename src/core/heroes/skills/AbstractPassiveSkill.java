package core.heroes.skills;

import core.client.GamePanel;
import ui.game.interfaces.SkillUI;

@SuppressWarnings("serial")
public abstract class AbstractPassiveSkill implements PassiveSkill {

	@Override
	public void onClientSkillLoaded(GamePanel panel, SkillUI skill) {
		// Passive skills do not require player action
	}

	@Override
	public void onClientSkillUnloaded(GamePanel panel, SkillUI skill) {
		// Passive skills do not require player action
	}

}
