package net;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1066337976243895551L;
	private final String name;
	private final int location;
	
	public UserInfo(String name, int location) {
		this.name = name;
		this.location = location;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLocation() {
		return location;
	}
	
}
