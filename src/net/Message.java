package net;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = -204111800079070139L;
	private final String text;
	
	public Message(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
