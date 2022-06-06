package net;

import java.io.Serializable;

import commands.Command;

public class CommandPacket implements Serializable {

	private static final long serialVersionUID = -6927509118983290799L;
	
	public static final int CONFIRM = -1;

	private final int id;
	private final Command<?, ? extends Connection> command;
	
	public CommandPacket(int id, Command<?, ? extends Connection> command) {
		this.id = id;
		this.command = command;
	}
	
	public int getID() {
		return id;
	}
	
	public Command<?, ? extends Connection> getCommand() {
		return command;
	}
	
}
