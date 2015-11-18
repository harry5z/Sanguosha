package net;

import java.io.Serializable;

import commands.Command;

public class CommandPacket implements Serializable {

	private static final long serialVersionUID = -6927509118983290799L;
	
	public static final int CONFIRM = -1;

	private final int id;
	private final Command<?> command;
	
	public CommandPacket(int id, Command<?> command) {
		this.id = id;
		this.command = command;
	}
	
	public int getID() {
		return id;
	}
	
	public Command<?> getCommand() {
		return command;
	}
	
}
