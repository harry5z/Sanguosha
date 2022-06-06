package ui.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import commands.server.LeaveRoomServerCommand;
import commands.server.StartGameServerCommand;
import core.Constants;
import core.client.ClientPanel;
import core.server.RoomInfo;
import net.Connection;
import net.UserInfo;
import net.client.ClientMessageListener;
import ui.client.components.ControlButtonGui;

public class RoomGui extends JPanel implements ClientPanel {
	private static final long serialVersionUID = 2664732095891084244L;
	private static final int HGAP = Constants.SCREEN_WIDTH / 160;
	private static final int VGAP = Constants.SCREEN_HEIGHT / 90;
	private final Connection connection;
	private final List<PlayerUI> players;
	
	public RoomGui(RoomInfo room, Connection connection) {
		this.connection = connection;
		this.players = new ArrayList<PlayerUI>();
		this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2));
		JPanel ctrlButtonsPanel = new JPanel();
		JButton leaveButton = new ControlButtonGui("Leave", e -> RoomGui.this.connection.send(new LeaveRoomServerCommand()));
		JButton startButton = new ControlButtonGui("Start", e -> RoomGui.this.connection.send(new StartGameServerCommand()));
		ctrlButtonsPanel.add(leaveButton);
		ctrlButtonsPanel.add(startButton);
		
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(2, 4, HGAP, VGAP));
		for (int i = 0; i < room.getRoomConfig().getCapacity(); i++) {
			PlayerUI ui = new PlayerUI(i, null); // TODO add userinfo to map
			players.add(ui);
			playerPanel.add(ui);
		}
		setLayout(new BorderLayout());
		add(playerPanel, BorderLayout.NORTH);
		add(ctrlButtonsPanel, BorderLayout.SOUTH);
	}
	
	public void updatePlayers(List<UserInfo> users) {
		for (PlayerUI ui : players) {
			ui.clearLabel();
		}
		for (UserInfo user : users) {
			players.get(user.getLocation()).updateUser(user);
		}
	}

	@Override
	public ClientMessageListener getMessageListener() {
		return null;
	}

	private static class PlayerUI extends JPanel {

		private static final long serialVersionUID = 5106908929115824543L;
		private JLabel nameLabel;
		private JLabel locationLabel;

		PlayerUI(int location, UserInfo info) {
			setPreferredSize(new Dimension(200, 200));
			if (info != null) {
				nameLabel = new JLabel(info.getName());
				locationLabel = new JLabel("Location " + location);
			} else {
				nameLabel = new JLabel(" ");
				locationLabel = new JLabel(" ");
			}
			add(nameLabel);
			add(locationLabel);
		}
		
		void updateUser(UserInfo user) {
			nameLabel.setText(user.getName());
		}
		
		void clearLabel() {
			nameLabel.setText("");
		}
	}

	@Override
	public JPanel getPanelUI() {
		return this;
	}
}
