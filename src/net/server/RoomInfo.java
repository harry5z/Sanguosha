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
	
	public RoomInfo(int id, RoomConfig config, int occupancy) {
		this.roomID = id;
		this.config = config;
		this.occupancy = occupancy;
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
	
	@Override
	public int hashCode() {
		return roomID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RoomInfo)) {
			return false;
		}
		return roomID == ((RoomInfo) obj).roomID;
	}
}
