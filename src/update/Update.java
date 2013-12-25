package update;

import java.io.Serializable;

import core.Master;
import core.Player;

public abstract class Update implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8023612171434442802L;

	public void masterOperation(Master master)
	{
		master.sendToAllClients(this);
	}
	public abstract void playerOperation(Player player);
}
