package ui.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.Constants;
import core.client.GamePanel;
import core.heroes.Hero;
import core.player.PlayerSimple;
import listeners.game.CardOnHandListener;
import listeners.game.EquipmentListener;
import listeners.game.HealthListener;
import listeners.game.HeroListener;
import ui.game.interfaces.PlayerUI;

/**
 * Gui of other players, shows life bar, equipments, count of cards on hand,
 * etc.
 * 
 * @author Harry
 *
 */
public class PlayerGui extends JPanel implements PlayerUI {

	private static final long serialVersionUID = 7793033583166480640L;

	public static final int HEIGHT = 200;
	public static final int WIDTH = 200;
	private static final int NAMETAG_HEIGHT = 30;
	private static final int PICTURE_HEIGHT = 70;
	private static final int LIFEBAR_HEIGHT = 20;
	private static final int EQUIPMENTBAR_HEIGHT = 20;
	private static final int CARDCOUNT_HEIGHT = 20;
	private PlayerSimple player;
	private boolean activated = false;
	private boolean chained = false;
	private boolean wineUsed = false;
	private BufferedImage chainedImage;
	private CountdownBarGui countdownBar;
	private JButton heroButton;

	public PlayerGui(PlayerSimple player, GamePanel panel) {
		this.player = player;
		setSize(WIDTH, HEIGHT + 60);
		setLayout(null);
		
		JLabel name = new JLabel(player.getName());
		name.setSize(WIDTH, NAMETAG_HEIGHT);
		name.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		name.setHorizontalAlignment(JLabel.CENTER);
		add(name);
		
		heroButton = new JButton();
		heroButton.setSize(WIDTH, PICTURE_HEIGHT);
		heroButton.setLocation(0, NAMETAG_HEIGHT);
		heroButton.setEnabled(false);
		heroButton.addActionListener(e -> panel.getCurrentOperation().onPlayerClicked(this));
		add(heroButton);

		PlayerCardGui cardsCount = new PlayerCardGui();
		player.registerCardOnHandListener(cardsCount);
		add(cardsCount);

		HorizontalLifebarGui lifebar = new HorizontalLifebarGui();
		player.registerHealthListener(lifebar);
		add(lifebar);

		PlayerEquipmentRackGui equipments = new PlayerEquipmentRackGui();
		player.registerEquipmentListener(equipments);
		add(equipments);
		
		DelayedBarGui delayedBar = new DelayedBarGui();
		delayedBar.setSize(200, 35);
		delayedBar.setLocation(0, HEIGHT);
		player.registerDelayedListener(delayedBar);
		add(delayedBar);
		
		countdownBar = new CountdownBarGui(WIDTH, 20);
		countdownBar.setLocation(0, HEIGHT + 40);
		add(countdownBar);
		
		player.registerHeroListener(new HeroListener() {
			@Override
			public void onHeroRegistered(Hero hero) {
				heroButton.setText(hero.getName());
			}

			@Override
			public void onWineEffective(boolean effective) {
				wineUsed = effective;
				repaint();
			}

		});

		try {
			this.chainedImage = ImageIO.read(getClass().getResource("cards/chained.png"));
		} catch (IOException e1) {
			System.err.println("File cards/chained.png not found");
		}
	}

	@Override
	public void setActivatable(boolean activatable) {
		heroButton.setEnabled(activatable);
	}
	
	@Override
	public void setActivated(boolean activated) {
		this.activated = activated;
		repaint();
	}

	private static class HorizontalLifebarGui extends JPanel implements HealthListener {

		private static final long serialVersionUID = 7133816476676033323L;

		public static final int WIDTH = PlayerGui.WIDTH;
		public static final int HEIGHT = PlayerGui.LIFEBAR_HEIGHT;
		private int limit;
		private boolean alive;
		private int current;

		public HorizontalLifebarGui() {
			setSize(WIDTH, HEIGHT);
			setLocation(0, NAMETAG_HEIGHT + PICTURE_HEIGHT);
			alive = true;
			limit = 0;
			current = 0;
		}

		@Override
		public synchronized void onSetHealthLimit(int limit) {
			this.limit = limit;
			repaint();
		}

		@Override
		public synchronized void onSetHealthCurrent(int current) {
			this.current = current;
			repaint();
		}

		@Override
		public synchronized void onHealthChangedBy(int amount) {
			current += amount;
			if (current > limit)// may not be necessary
				current = limit;
			repaint();
		}

		@Override
		public synchronized void onDeath() {
			alive = false;
			JLabel death = new JLabel("Dead");
			death.setSize(WIDTH, HEIGHT);
			death.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
			add(death);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (alive) {
				g.drawRect(0, 0, WIDTH, HEIGHT);
				if (limit == 0)
					return;
				int diameter = WIDTH / (limit * 2 + 1);
				int y = (HEIGHT - diameter) / 2;
				for (int i = 0; i < limit; i++) {
					if (i < current)
						g.setColor(Color.GREEN);
					else
						g.setColor(Color.RED);
					g.fillOval((2 * i + 1) * diameter, y, diameter, diameter);
				}
			}
		}
	}

	private static class PlayerEquipmentRackGui extends JPanel implements EquipmentListener {

		private static final long serialVersionUID = -157796487907126040L;

		public static final int WIDTH = PlayerGui.WIDTH;
		public static final int HEIGHT = 4 * PlayerGui.EQUIPMENTBAR_HEIGHT;
		private EquipmentGui weapon;
		private EquipmentGui shield;
		private EquipmentGui horsePlus;
		private EquipmentGui horseMinus;

		public PlayerEquipmentRackGui() {
			setSize(WIDTH, HEIGHT);
			setLayout(null);
			setLocation(0, PlayerGui.HEIGHT - HEIGHT);
			weapon = new EquipmentGui(0, 10);
			shield = new EquipmentGui(PlayerGui.EQUIPMENTBAR_HEIGHT, 10);
			horsePlus = new EquipmentGui(2 * PlayerGui.EQUIPMENTBAR_HEIGHT, 10);
			horseMinus = new EquipmentGui(3 * PlayerGui.EQUIPMENTBAR_HEIGHT, 10);
			weapon.setSize(WIDTH, PlayerGui.EQUIPMENTBAR_HEIGHT);
			shield.setSize(WIDTH, PlayerGui.EQUIPMENTBAR_HEIGHT);
			horsePlus.setSize(WIDTH, PlayerGui.EQUIPMENTBAR_HEIGHT);
			horseMinus.setSize(WIDTH, PlayerGui.EQUIPMENTBAR_HEIGHT);
			add(weapon);
			add(shield);
			add(horsePlus);
			add(horseMinus);
		}

		@Override
		public void onEquipped(Equipment equipment) {
			switch (equipment.getEquipmentType()) {
				case WEAPON:
					weapon.setEquipment(equipment);
					break;
				case SHIELD:
					shield.setEquipment(equipment);
					break;
				case HORSEPLUS:
					horsePlus.setEquipment(equipment);
					break;
				case HORSEMINUS:
					horseMinus.setEquipment(equipment);
					break;
				default:
					System.err.println("EquipmentRack: Unidentified Error");
			}
		}

		@Override
		public void onUnequipped(EquipmentType type) {
			switch (type) {
				case WEAPON:
					weapon.setEquipment(null);
					break;
				case SHIELD:
					shield.setEquipment(null);
					break;
				case HORSEPLUS:
					horsePlus.setEquipment(null);
					break;
				case HORSEMINUS:
					horseMinus.setEquipment(null);
					break;
				default:
					System.err.println("EquipmentRack: Unidentified Error");
			}
		}
	}

	private static class PlayerCardGui extends JLabel implements CardOnHandListener {

		private static final long serialVersionUID = 4479061341666503427L;
		
		private int count;

		public PlayerCardGui() {
			super("0");
			count = 0;
			setSize(CARDCOUNT_HEIGHT, CARDCOUNT_HEIGHT);
			setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			setHorizontalAlignment(JLabel.CENTER);
		}

		@Override
		public synchronized void onCardAdded(Card card) {
			count++;
			setText("" + count);
		}

		@Override
		public synchronized void onCardRemoved(Card card) {
			count--;
			setText("" + count);
		}
	}

	@Override
	public PlayerSimple getPlayer() {
		return player;
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
			g.fillRect(0, NAMETAG_HEIGHT, WIDTH, PICTURE_HEIGHT);
		}
	}

	@Override
	public void showCountdownBar(int timeMS) {
		countdownBar.countdown(timeMS);
	}
	
	@Override
	public void stopCountdown() {
		countdownBar.stopCountdown();
	}

	@Override
	public void setFlipped(boolean flipped) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setChained(boolean chained) {
		this.chained = chained;
		this.validate();
		this.repaint();
	}
}
