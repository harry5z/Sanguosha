package ui.game;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import core.client.GamePanel;
import core.heroes.skills.Skill;

@SuppressWarnings("serial")
public class SkillBarGui extends JPanel {
	
	public static final int WIDTH = HeroGui.WIDTH;
	public static final int HEIGHT = 50;
	private Set<SkillGui> skills;
	
	public SkillBarGui() {
		this.setLayout(null);
		this.skills = new HashSet<>();
		this.setSize(new Dimension(WIDTH, HEIGHT));
	}

	public void addSkill(Skill skill, GamePanel panel) {
		SkillGui ui = new SkillGui(skill);
		ui.onAdded(panel);
		this.skills.add(ui);
		this.add(ui);
		this.relocate();
		this.validate();
		this.repaint();
	}
	
	public void removeSkill(Skill skill) {
		for (SkillGui ui : this.skills) {
			if (ui.getSkill().equals(skill)) {
				this.skills.remove(ui);
				this.remove(ui);
				this.relocate();
				this.validate();
				this.repaint();
				return;
			}
		};
	}
	
	private void relocate() {
		int x = 0;
		for (SkillGui g : this.skills) {
			g.setLocation(x, 0);
			x += WIDTH / this.skills.size();
			g.setSize(new Dimension(WIDTH / this.skills.size(), HEIGHT));
		};
	}
}
