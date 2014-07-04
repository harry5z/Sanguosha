package gui.net.server;

import gui.net.ControlButtonGui;
import gui.net.LabelGui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.Connection;
import net.Message;
import net.client.ClientPanel;
import net.server.RoomInfo;

import commands.lobby_commands.CreateRoomServerCommand;
import commands.lobby_commands.EnterRoomServerCommand;

public class LobbyGui extends JPanel implements ClientPanel {
	private static final long serialVersionUID = 2778859336381008271L;
	private final Connection connection;
	
	public LobbyGui(List<RoomInfo> rooms, Connection connection) {
		this.connection = connection;
		JPanel roomsPanel = new JPanel(new GridLayout(0,1));
		rooms.sort((room1, room2) -> Integer.compare(room1.getRoomID(), room2.getRoomID())); 
		for(RoomInfo room : rooms) 
			roomsPanel.add(new RoomGui(room));
		JPanel addRoom = new JPanel(new GridLayout(0,1));
		JButton newRoom = new ControlButtonGui("New Room", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LobbyGui.this.connection.send(new CreateRoomServerCommand(null, null));
			}
		});
		addRoom.add(newRoom);
		
		add(roomsPanel);
		add(addRoom);
	}
	
	private class RoomGui extends JButton {
		private static final long serialVersionUID = -7104524626511953037L;

		RoomGui(RoomInfo room) {
			int id = room.getRoomID();
			String name = room.getRoomConfig().getName();
			int capacity = room.getRoomConfig().getCapacity();
			int occupancy = room.getOccupancy();
			boolean chat = room.getRoomConfig().isChatAllowed();
			System.out.println("Room "+id+"  ppl: "+occupancy);
			
			JLabel roomID = new LabelGui("Room "+id);
			JLabel nameTag = new LabelGui(name);
			JLabel occupany = new LabelGui(occupancy+"/"+capacity);
			JLabel chattable = new LabelGui(chat ? "聊天" : "禁聊");
			
			this.setBorder(new LineBorder(Color.BLACK, 2));
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
			add(roomID);
			add(nameTag);
			add(occupany);
			add(chattable);
			
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						connection.send(new EnterRoomServerCommand(id));
					}
				}
			});
		}
		
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
