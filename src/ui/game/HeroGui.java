package ui.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

import core.Constants;
import core.client.GamePanel;
import core.heroes.Hero;
import core.heroes.original.HeroOriginal;
import ui.game.interfaces.HeroUI;

public class HeroGui extends JButton implements HeroUI<HeroOriginal> {

	private static final long serialVersionUID = 5926706015812873971L;
	
	private HeroOriginal hero;
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	private boolean activated = false;

	public HeroGui(GamePanel<? extends Hero> panel) {
		hero = null;
		setEnabled(false);
		setSize(WIDTH, HEIGHT);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		setLocation(GamePanelGui.WIDTH - LifebarGui.WIDTH - WIDTH, GamePanelGui.HEIGHT - HEIGHT);
		addActionListener(e -> panel.getCurrentOperation().onSelfClicked(this));
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

	@Override
	public synchronized void setHero(HeroOriginal hero) {
		this.hero = hero;
		setText(hero.getName());
		repaint();
	}

	@Override
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
