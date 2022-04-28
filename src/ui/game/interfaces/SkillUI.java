package ui.game.interfaces;

import core.heroes.skills.Skill;

public interface SkillUI extends Activatable {

	public Skill getSkill();
	
	public void setActionOnActivation(Runnable action);
	
}
