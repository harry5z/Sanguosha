package ui.game;

import heroes.original.HeroOriginal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

import net.client.GamePanel;
import core.Constants;

public class HeroGui extends JButton implements Activatable {

	private static final long serialVersionUID = 5926706015812873971L;
	
	private HeroOriginal hero;
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	private boolean activated = false;

	public HeroGui(GamePanel panel) {
		hero = null;
		setEnabled(false);
		setSize(WIDTH, HEIGHT);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		setLocation(GamePanelUI.WIDTH - LifebarGui.WIDTH - WIDTH, GamePanelUI.HEIGHT - HEIGHT);
		addActionListener(e -> panel.getCurrentOperation().onSelfClicked(this));
	}

	protected void select() {
		activated = true;
		repaint();
	}

	protected void unselect() {
		activated = false;
		repaint();
	}
	
	@Override
	public synchronized void setActivatable(boolean activatable) {
		setEnabled(activatable);
	}

	@Override
	public synchronized void setActivated(boolean activated) {
		this.activated = activated;
		repaint();
	}

	public synchronized void setHero(HeroOriginal hero) {
		this.hero = hero;
		setText(hero.getName());
		repaint();
	}

	public synchronized HeroOriginal getHero() {
		return hero;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (activated)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		((Graphics2D) g).setStroke(new BasicStroke(Constants.BORDER_WIDTH));
		g.drawRect(0, 0, WIDTH, HEIGHT);
	}
}
