package net.server;

import java.io.Serializable;

/**
 * This class defines the setup of a room, e.g. capacity, allow_chat, etc.
 * 
 * @author Harry
 * 
 */
public class RoomConfig implements Serializable {
	private static final long serialVersionUID = -688290922166457137L;
	private int capacity;
	private boolean chatAllowed;
	private String name;

	/**
	 * Default configuration:
	 * <ul>
	 * <li>Capacity : 8 people</li>
	 * <li>chatAllowed : true</li>
	 * <li>name : "Sanguosha Room"</li>
	 * </ul>
	 */
	public RoomConfig() {
		this.capacity = 8;
		this.chatAllowed = true;
		this.name = "Sanguosha Room";
	}

	/**
	 * Non-default constructor for room
	 * 
	 * @param capacity : initial capacity of room
	 * @param chatAllowed : is chatting allowed?
	 * @param name : room name
	 */
	public RoomConfig(int capacity, boolean chatAllowed, String name) {
		this.capacity = capacity;
		this.chatAllowed = chatAllowed;
		this.name = name;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public void setChatAllowed(boolean isAllowed) {
		this.chatAllowed = isAllowed;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean isChatAllowed() {
		return chatAllowed;
	}
	
	public String getName() {
		return name;
	}
}
