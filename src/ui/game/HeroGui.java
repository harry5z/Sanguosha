package ui.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import core.Constants;
import core.client.GamePanel;
import core.heroes.Hero;
import core.heroes.original.HeroOriginal;
import core.player.PlayerCompleteClient;
import core.player.PlayerSimple;
import ui.game.interfaces.HeroUI;

public class HeroGui extends JButton implements HeroUI<HeroOriginal> {

	private static final long serialVersionUID = 5926706015812873971L;
	
	private PlayerCompleteClient self;
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	private boolean activated = false;
	private JComponent chain = null;

	public HeroGui(GamePanel<? extends Hero> panel) {
		setEnabled(false);
		setSize(WIDTH, HEIGHT);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		setLocation(GamePanelGui.WIDTH - LifebarGui.WIDTH - WIDTH, GamePanelGui.HEIGHT - HEIGHT);
		addActionListener(e -> panel.getCurrentOperation().onPlayerClicked(this));
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
	public synchronized void setPlayer(PlayerCompleteClient player) {
		this.self = player;
		setText(player.getHero().getName());
		repaint();
	}

	@Override
	public synchronized HeroOriginal getHero() {
		return this.self.getHero();
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

	@Override
	public PlayerSimple getPlayer() {
		return this.self;
	}

	@Override
	public void showCountdownBar() {
		// TODO implement
	}

	@Override
	public void setWineUsed(boolean used) {
		// TODO implement
	}

	@Override
	public void onWineUsed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttackUsed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetAttackLimit(int limit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetAttackUsed(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetWineUsed(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResetWineEffective() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFlip(boolean flipped) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFlipped(boolean flipped) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setChained(boolean chained) {
		if (!chained && this.chain != null) {
			this.remove(this.chain);
			this.chain = null;
		} else if (chained) {
			JLabel chain = new JLabel("Chained");
			chain.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			chain.setSize(WIDTH, 30);
			chain.setHorizontalAlignment(CENTER);
			chain.setAlignmentY(TOP_ALIGNMENT);
			this.chain = chain;
			this.add(chain);
		}
	}

	@Override
	public void onChained(boolean chained) {
		this.setChained(chained);
	}
}
