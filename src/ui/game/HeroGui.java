package ui.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Constants;
import core.client.GamePanel;
import core.heroes.Hero;
import core.heroes.skills.Skill;
import core.player.PlayerCompleteClient;
import core.player.PlayerSimple;
import core.player.PlayerState;
import core.player.Role;
import ui.game.interfaces.HeroUI;

@SuppressWarnings("serial")
public class HeroGui extends JPanel implements HeroUI {
	
	private PlayerCompleteClient self;
	public static final int WIDTH = CardRackGui.HEIGHT;
	public static final int HEIGHT = WIDTH;
	private static final int NAMETAG_HEIGHT = 30;
	private boolean activated = false;
	private boolean chained = false;
	private boolean wineUsed = false;
	private BufferedImage chainedImage;
	private SkillBarGui skillBar = null;
	private final GamePanel panel;
	private final JButton heroButton;
	private final JLabel nameLabel;

	public HeroGui(GamePanel panel) {
		this.panel = panel;
		this.setLayout(null);
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(GamePanelGui.WIDTH - LifebarGui.WIDTH - WIDTH, GamePanelGui.HEIGHT - HEIGHT);
		
		nameLabel = new JLabel();
		nameLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setSize(WIDTH, NAMETAG_HEIGHT);
		nameLabel.setLocation(0, 0);
		add(nameLabel);
				
		JButton hero = new JButton();
		hero.setSize(new Dimension(WIDTH, HEIGHT - SkillBarGui.HEIGHT - NAMETAG_HEIGHT));
		hero.setLocation(0, NAMETAG_HEIGHT);
		hero.setEnabled(false);
		hero.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		hero.addActionListener(e -> panel.getCurrentOperation().onPlayerClicked(this));
		this.add(hero);
		this.heroButton = hero;
		
		this.skillBar = new SkillBarGui();
		this.skillBar.setLocation(0, HEIGHT - SkillBarGui.HEIGHT);
		this.add(skillBar);
		
		try {
			this.chainedImage = ImageIO.read(getClass().getResource("cards/chained.png"));
		} catch (IOException e1) {
			System.err.println("File cards/chained.png not found");
		}
	}

	@Override
	public synchronized void setActivatable(boolean activatable) {
		this.heroButton.setEnabled(activatable);
	}

	@Override
	public synchronized void setActivated(boolean activated) {
		this.activated = activated;
		repaint();
	}

	@Override
	public synchronized void setPlayer(PlayerCompleteClient player) {
		this.self = player;
		nameLabel.setText(player.getName());
		repaint();
	}
	
	@Override
	public void onHeroRegistered(Hero hero) {
		this.heroButton.setText(hero.getName());
		for (Skill skill : hero.getSkills()) {
			this.skillBar.addSkill(skill, this.panel);
		}
		this.validate();
		this.repaint();
	}

	@Override
	public synchronized Hero getHero() {
		return this.self.getHero();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (chained) {
			g.drawImage(chainedImage, 0, 25, null);
		}
		g.setColor(activated ? Color.RED : Color.BLACK);
		((Graphics2D) g).setStroke(new BasicStroke(Constants.BORDER_WIDTH));
		g.drawRect(0, 0, WIDTH, HEIGHT);
		
		if (wineUsed) {
			g.setColor(Color.RED);
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.3);
			((Graphics2D) g).setComposite(ac);
			g.fillRect(0, 0, WIDTH, HEIGHT - SkillBarGui.HEIGHT);
		}
	}

	@Override
	public PlayerSimple getPlayer() {
		return this.self;
	}

	@Override
	public void showCountdownBar(int timeMS) {
		
	}
	
	@Override
	public void stopCountdown() {
		
	}

	@Override
	public void onAttackUsed() {
		// nothing to show
	}

	@Override
	public void onSetAttackLimit(int limit) {
		// nothing to show
	}

	@Override
	public void onSetAttackUsed(int amount) {
		// nothing to show
	}

	@Override
	public void onSetWineUsed(int amount) {
		// nothing to show
	}

	@Override
	public void onWineEffective(boolean effective) {
		this.wineUsed = effective;
		repaint();
	}

	@Override
	public void onFlip(boolean flipped) {
		// TODO add "flipped" layer
		
	}

	@Override
	public void setFlipped(boolean flipped) {
		// TODO add "flipped" layer
	}

	@Override
	public void setChained(boolean chained) {
		this.chained = chained;
		this.validate();
		this.repaint();
	}
	
	@Override
	public void onChained(boolean chained) {
		this.setChained(chained);
	}

	@Override
	public void onPlayerStateUpdated(PlayerState state, int value) {
		// Nothing to do
	}

	@Override
	public void onRoleAssigned(Role role) {
		nameLabel.setText(self.getName() + "(" + role + ")");
	}
	
	@Override
	public void onRoleRevealed(Role role) {
		// One always knows their own role
	}

}
