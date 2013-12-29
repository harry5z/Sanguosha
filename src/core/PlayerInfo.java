package core;

import java.io.Serializable;

public class PlayerInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6855827509699547590L;
	private String name;
	private int position;
	public PlayerInfo(String name,int position)
	{
		this.name = name;
		this.position = position;
	}
	public String getName()
	{
		return name;
	}
	public int getPosition()
	{
		return position;
	}
}
