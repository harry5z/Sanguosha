package net.server;

import java.io.Serializable;

/**
 * This class is used for shipping server room info to client
 * @author Harry
 *
 */
public final class RoomInfo implements Serializable {
	private static final long serialVersionUID = 1837425406005838659L;
	private final int roomID;
	private int occupancy;
	private final RoomConfig config;
	
	public RoomInfo(int id, RoomConfig config) {
		this.roomID = id;
		this.config = config;
		this.occupancy = 0;
	}
	
	RoomInfo setOccupancy(int number) { 
		this.occupancy = number;
		return this;
	}
	
	public int getOccupancy() {
		return occupancy;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public RoomConfig getRoomConfig() {
		return config;
	}
}
