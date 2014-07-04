package gui.net.server;

import java.awt.Dimension;

import javax.swing.JPanel;

import net.Connection;
import net.Message;
import net.client.ClientPanel;
import net.server.RoomInfo;
import core.Constants;

public class RoomGui extends JPanel implements ClientPanel {
	private static final long serialVersionUID = 2664732095891084244L;
	private final RoomInfo room;
	private final Connection connection;
	
	public RoomGui(RoomInfo room, Connection connection) {
		this.room = room;
		this.connection = connection;
		this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2));
		
	}
	@Override
	public void onConnectionFailed(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionSuccessful() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessageReceived(Message message) {
		// TODO Auto-generated method stub

	}

	@Override
	public JPanel getContent() {
		return this;
	}

}
