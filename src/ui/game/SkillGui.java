package ui.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

import core.Constants;
import core.client.GamePanel;
import core.heroes.skills.Skill;
import ui.game.interfaces.SkillUI;

@SuppressWarnings("serial")
public class SkillGui extends JButton implements SkillUI {
	
	private final Skill skill;
	private Runnable action;
	private boolean activated;

	public SkillGui(Skill skill) {
		this.skill = skill;
		this.activated = false;
		this.addActionListener(e -> {
			if (this.action == null) {
				// TODO handle it better
				throw new RuntimeException("Skill Button has no action");
			}
			this.action.run();
		});
		this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		this.setText(skill.getName());
		this.setEnabled(false);
	}
	
	public void onAdded(GamePanel panel) {
		this.skill.onClientSkillLoaded(panel, this);
	}
	
	public void onRemoved(GamePanel panel) {
		this.skill.onClientSkillUnloaded(panel, this);
	}

	@Override
	public void setActivated(boolean activated) {
		this.activated = activated;
		repaint();		
	}

	@Override
	public void setActivatable(boolean activatable) {
		this.setEnabled(activatable);
	}

	@Override
	public Skill getSkill() {
		return this.skill;
	}

	@Override
	public void setActionOnActivation(Runnable action) {
		this.action = action;
	}
	
	@Override
	public void paint(Graphics g) {
	  super.paint(g);
	  if (this.activated) {
		  Graphics2D graphics = (Graphics2D) g;
		  graphics.setColor(Color.RED);
		  graphics.setStroke(new BasicStroke(Constants.BORDER_WIDTH));
		  graphics.drawRect(0, 0, this.getWidth(), this.getHeight());
	  }
	}

}
