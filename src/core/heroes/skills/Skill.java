package core.heroes.skills;

import java.io.Serializable;

import core.client.GamePanel;
import ui.game.interfaces.SkillUI;

public interface Skill extends Serializable {

	public String getName();

	public String getDescription();
	
	public void onClientSkillLoaded(GamePanel panel, SkillUI skill);
	
	public void onClientSkillUnloaded(GamePanel panel, SkillUI skill);
}
