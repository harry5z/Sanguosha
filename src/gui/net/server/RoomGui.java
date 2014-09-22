package gui.net.server;

import gui.net.ControlButtonGui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import commands.lobby.CreateRoomLobbyServerCommand;
import commands.room.LeaveRoomServerCommand;
import net.Connection;
import net.client.ClientMessageListener;
import net.client.ClientPanel;
import net.server.RoomInfo;
import core.Constants;

public class RoomGui extends JPanel implements ClientPanel<RoomGui> {
	private static final long serialVersionUID = 2664732095891084244L;
	private final RoomInfo room;
	private final Connection connection;
	
	public RoomGui(RoomInfo room, Connection connection) {
		this.room = room;
		this.connection = connection;
		this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2));
		JButton leave = new ControlButtonGui("Leave", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomGui.this.connection.send(new LeaveRoomServerCommand());
			}
		});
		add(leave);
	}

	@Override
	public RoomGui getContent() {
		return this;
	}

	@Override
	public ClientMessageListener getMessageListener() {
		return null;
	}

}
