package update;

import java.io.Serializable;

import core.Framework;

public abstract class Update implements Serializable
{
	public static final String GAME_OVER = "Game Over";
	
	private String message;
	
	public String getMessage()
	{
		return message;
	}
	
	public abstract void update(Framework framwork);
}
