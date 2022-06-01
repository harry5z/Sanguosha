package ui.game;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Constants;
import core.GameState;
import core.client.GamePanel;
import core.player.PlayerCompleteClient;
import core.player.PlayerInfo;
import core.player.PlayerSimple;
import ui.game.interfaces.CardRackUI;
import ui.game.interfaces.GameUI;
import ui.game.interfaces.HeroUI;
import ui.game.interfaces.PlayerUI;

public class GamePanelGui extends JPanel implements GameUI, GameState {
	private static final long serialVersionUID = 2519723480954332278L;

	public static final int WIDTH = Constants.SCREEN_WIDTH / 4 * 3;
	public static final int HEIGHT = Constants.SCREEN_HEIGHT / 4 * 3;

	private CardRackGui cardRack;
	private EquipmentRackGui equipmentRack;
	private HeroGui heroGui;
	private LifebarGui healthGui;
	private CardDisposalGui disposalGui;
	private DelayedBarGui delayedGui;
	private JPanel customizedSelectionPane;

	private PlayerCompleteClient myself;
	private List<PlayerGui> otherPlayers;
	private ButtonGui confirm;
	private ButtonGui cancel;
	private ButtonGui end;

	private JLabel deckSize;
	private MessageBoxGui messageBox;
	private CountdownBarGui countdownBar;
	
	private final GamePanel panel;
	
	public GamePanelGui(PlayerInfo player, GamePanel panel) {
		this.panel = panel;
		setLayout(null);
		myself = new PlayerCompleteClient(player.getName(), player.getPosition());

		cardRack = new CardRackGui(panel);
		equipmentRack = new EquipmentRackGui(panel);
		heroGui = new HeroGui(panel);
		healthGui = new LifebarGui();
		disposalGui = new CardDisposalGui(this);
		delayedGui = new DelayedBarGui();
		otherPlayers = new ArrayList<>();
		deckSize = new JLabel();
		deckSize.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		deckSize.setSize(100, 100);
		deckSize.setLocation(WIDTH - 100, PlayerGui.HEIGHT);
		delayedGui.setSize(120, 35);
		delayedGui.setLocation(WIDTH - 120, HEIGHT - CardRackGui.HEIGHT - 35);
		messageBox = new MessageBoxGui();
		messageBox.setLocation(equipmentRack.getWidth(), HEIGHT - cardRack.getHeight() - MessageBoxGui.HEIGHT);
		countdownBar = new CountdownBarGui(CardRackGui.WIDTH - ButtonGui.WIDTH * 2, 30);
		countdownBar.setLocation(equipmentRack.getWidth() + ButtonGui.WIDTH, HEIGHT - cardRack.getHeight() - MessageBoxGui.HEIGHT - 40);

		myself.registerCardOnHandListener(cardRack);
		myself.registerEquipmentListener(equipmentRack);
		myself.registerHealthListener(healthGui);
		myself.registerCardDisposalListener(disposalGui);
		myself.registerDelayedListener(delayedGui);
		myself.registerHeroListener(heroGui);
		heroGui.setPlayer(myself);
		confirm = new ButtonGui("Confirm", e -> panel.getCurrentOperation().onConfirmed());
		confirm.setLocation(0, HEIGHT - CardRackGui.HEIGHT - ButtonGui.HEIGHT);
		cancel = new ButtonGui("Cancel", e -> panel.getCurrentOperation().onCanceled());
		cancel.setLocation(ButtonGui.WIDTH, HEIGHT - CardRackGui.HEIGHT - ButtonGui.HEIGHT);
		end = new ButtonGui("End", e -> panel.getCurrentOperation().onEnded());
		end.setLocation(ButtonGui.WIDTH * 2, HEIGHT - CardRackGui.HEIGHT - ButtonGui.HEIGHT);
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(cardRack);
		add(equipmentRack);
		add(heroGui);
		add(healthGui);
		add(disposalGui);
		add(delayedGui);
		add(confirm);
		add(cancel);
		add(end);
		add(deckSize);
		add(messageBox);
		add(countdownBar);
	}

	public synchronized void addPlayer(PlayerInfo info) {
		PlayerSimple player = new PlayerSimple(info.getName(), info.getPosition());
		player.registerCardDisposalListener(disposalGui);
		PlayerGui p = new PlayerGui(player, panel);
		
		otherPlayers.forEach(ui -> this.remove(ui));
		otherPlayers.add(p);
		int myPosition = myself.getPosition();
		int size = otherPlayers.size();
		// players with position higher than self must be at the front, as they act after oneself
		otherPlayers.sort((p1, p2) -> {
			return ((p1.getPlayer().getPosition() + size - myPosition) % (size + 1)) -
					((p2.getPlayer().getPosition() + size - myPosition) % (size + 1));
		});
		
		if (size == 1) {
			otherPlayers.get(0).setLocation(WIDTH / 2 - PlayerGui.WIDTH / 2, 0);
		} else if (size == 2) {
			otherPlayers.get(0).setLocation(WIDTH / 3 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(1).setLocation(WIDTH / 3 * 2 - PlayerGui.WIDTH / 2, 0);
		} else if (size == 3) {
			otherPlayers.get(0).setLocation(0, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
			otherPlayers.get(1).setLocation(WIDTH / 2 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(2).setLocation(WIDTH - PlayerGui.WIDTH, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
		} else if (size == 4) {
			otherPlayers.get(0).setLocation(0, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
			otherPlayers.get(1).setLocation(WIDTH / 3 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(2).setLocation(WIDTH / 3 * 2 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(3).setLocation(WIDTH - PlayerGui.WIDTH, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
		} else if (size == 5) {
			otherPlayers.get(0).setLocation(0, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
			otherPlayers.get(1).setLocation(WIDTH / 4 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(2).setLocation(WIDTH / 4 * 2 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(3).setLocation(WIDTH / 4 * 3 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(4).setLocation(WIDTH - PlayerGui.WIDTH, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
		} else if (size == 6) {
			otherPlayers.get(0).setLocation(0, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
			otherPlayers.get(1).setLocation(0, 0);
			otherPlayers.get(2).setLocation(WIDTH / 3 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(3).setLocation(WIDTH / 3 * 2 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(4).setLocation(WIDTH - PlayerGui.WIDTH, 0);
			otherPlayers.get(5).setLocation(WIDTH - PlayerGui.WIDTH, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
		} else if (size == 7) {
			otherPlayers.get(0).setLocation(0, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
			otherPlayers.get(1).setLocation(0, 0);
			otherPlayers.get(2).setLocation(WIDTH / 4 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(3).setLocation(WIDTH / 4 * 2 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(4).setLocation(WIDTH / 4 * 3 - PlayerGui.WIDTH / 2, 0);
			otherPlayers.get(5).setLocation(WIDTH - PlayerGui.WIDTH, 0);
			otherPlayers.get(6).setLocation(WIDTH - PlayerGui.WIDTH, HEIGHT / 2 - PlayerGui.HEIGHT / 2);
		} // total number of player would not exceed 8 (i.e. max 7 "other players")
		otherPlayers.forEach(ui -> this.add(ui));
	}

	@Override
	public CardRackUI getCardRackUI() {
		return cardRack;
	}
	
	@Override
	public EquipmentRackGui getEquipmentRackUI() {
		return this.equipmentRack;
	}

	@Override
	public HeroUI getHeroUI() {
		return heroGui;
	}
	
	@Override
	public List<PlayerUI> getOtherPlayersUI() {
		return new ArrayList<>(otherPlayers);
	}
	
	@Override
	public List<PlayerSimple> getOtherPlayers() {
		return otherPlayers.stream().map(ui -> ui.getPlayer()).collect(Collectors.toList());
	}
	
	@Override
	public PlayerCompleteClient getSelf() {
		return myself;
	}
	
	@Override
	public int getNumberOfPlayers() {
		return otherPlayers.size() + 1;
	}
	
	@Override
	public int getNumberOfPlayersAlive() {
		int count = this.myself.isAlive() ? 1 : 0;
		for (PlayerUI player : this.otherPlayers) {
			if (player.getPlayer().isAlive()) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public synchronized PlayerUI getOtherPlayerUI(PlayerInfo other) {
		for (PlayerUI ui : otherPlayers) {
			if (ui.getPlayer().getPlayerInfo().equals(other)) {
				return ui;
			}
		}
		throw new RuntimeException("No Other player ui found");
	}
	
	@Override
	public synchronized PlayerUI getOtherPlayerUI(String name) {
		for (PlayerUI ui : otherPlayers) {
			if (ui.getPlayer().getPlayerInfo().getName().equals(name)) {
				return ui;
			}
		}
		throw new RuntimeException("No Other player ui found");
	}
	
	@Override
	public PlayerSimple getPlayer(String name) {
		for (PlayerUI ui : otherPlayers) {
			if (ui.getPlayer().getName().equals(name)) {
				return ui.getPlayer();
			}
		}
		return null; // throw later
	}

	@Override
	public void setConfirmEnabled(boolean isEnabled) {
		confirm.setEnabled(isEnabled);
	}

	@Override
	public void setCancelEnabled(boolean isEnabled) {
		cancel.setEnabled(isEnabled);
	}
	
	@Override
	public void setEndEnabled(boolean isEnabled) {
		end.setEnabled(isEnabled);
	}

	@Override
	public void setMessage(String message) {
		messageBox.setMessage(message);
	}

	@Override
	public void clearMessage() {
		messageBox.clearMessage();
	}

	@Override
	public void displayCustomizedSelectionPaneAtCenter(JPanel panel) {
		this.removeSelectionPane();
		customizedSelectionPane = panel;
		customizedSelectionPane.setLocation((WIDTH - customizedSelectionPane.getWidth()) / 2,
				(HEIGHT - CardRackGui.HEIGHT - customizedSelectionPane.getHeight()) / 2);
		add(customizedSelectionPane);
		setComponentZOrder(customizedSelectionPane, 0); // foremost
		customizedSelectionPane.validate();
		customizedSelectionPane.repaint();
	}
	
	@Override
	public JPanel getSelectionPane() {
		return this.customizedSelectionPane;
	}

	@Override
	public void removeSelectionPane() {
		if (this.customizedSelectionPane != null) {
			this.remove(this.customizedSelectionPane);
			this.customizedSelectionPane = null;
			this.validate();
			this.repaint();
		}
	}
	
	public void showCountdownBar(int timeMS) {
		countdownBar.countdown(timeMS);
	}
	
	public void stopCountdown() {
		countdownBar.stopCountdown();
	}
	
}