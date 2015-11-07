package ui.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.Connection;
import net.client.ClientMessageListener;
import net.client.ClientPanel;
import net.server.RoomInfo;
import ui.client.components.ControlButtonGui;
import ui.client.components.LabelGui;
import commands.lobby.CreateRoomLobbyServerCommand;
import commands.lobby.EnterRoomLobbyServerCommand;

public class LobbyGui extends JPanel implements ClientPanel<LobbyGui> {
	private static final long serialVersionUID = 2778859336381008271L;
	private static final Comparator<RoomInfo> COMPARATOR = (room1, room2) -> Integer.compare(room1.getRoomID(), room2.getRoomID());
	private final Connection connection;
	private List<RoomInfo> rooms;
	private JPanel roomsPanel;
	
	public LobbyGui(List<RoomInfo> rooms, Connection connection) {
		this.connection = connection;
		this.roomsPanel = new JPanel(new GridLayout(0,1));
		rooms.sort(COMPARATOR); 
		this.rooms = new ArrayList<RoomInfo>(rooms);
		for(RoomInfo room : rooms) 
			roomsPanel.add(new RoomGui(room));
		JPanel addRoom = new JPanel(new GridLayout(0,1));
		JButton newRoom = new ControlButtonGui("New Room", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LobbyGui.this.connection.send(new CreateRoomLobbyServerCommand(null, null));
			}
		});
		addRoom.add(newRoom);
		add(roomsPanel);
		add(addRoom);
	}
	
	public void updateRoom(RoomInfo newRoom) {
		int index = rooms.indexOf(newRoom);
		if (index == -1) {
			rooms.add(newRoom);
			rooms.sort(COMPARATOR);
		} else {
			rooms.set(index, newRoom);
		}
		roomsPanel.removeAll();
		for (RoomInfo room: rooms) {
			roomsPanel.add(new RoomGui(room));
		}
		roomsPanel.revalidate();
	}
	
	public void removeRoom(RoomInfo room) {
		if (rooms.remove(room)) {
			for (Component gui : roomsPanel.getComponents()) {
				if (gui != null && gui instanceof RoomGui && ((RoomGui) gui).room.equals(room)) {
					roomsPanel.remove(gui);
					break;
				}
			}
			roomsPanel.revalidate();
		}
	}
	
	@Override
	public LobbyGui getContent() {
		return this;
	}

	@Override
	public ClientMessageListener getMessageListener() {
		return null;
	}

	private class RoomGui extends JButton {
		private static final long serialVersionUID = -7104524626511953037L;
		final RoomInfo room;
		RoomGui(RoomInfo room) {
			this.room = room;
			int id = room.getRoomID();
			String name = room.getRoomConfig().getName();
			int capacity = room.getRoomConfig().getCapacity();
			int occupancy = room.getOccupancy();
			boolean chat = room.getRoomConfig().isChatAllowed();
			
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
						connection.send(new EnterRoomLobbyServerCommand(id));
					}
				}
			});
		}
		
		@Override
		public int hashCode() {
			return room.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof RoomGui)) 
				return false;
			return room.equals(((RoomGui) obj).room);
		}
	}

}
