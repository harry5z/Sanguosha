package update;

import java.io.Serializable;

import core.Card;
import core.Master;
import core.Player;

public abstract class Update implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8023612171434442802L;
	/**
	 * Default behavior of master-side operation: send the update to all players
	 * @param master
	 */
	public void masterOperation(Master master)
	{
		master.sendToAllClients(this);
	}
	public abstract void playerOperation(Player player);
	
	public abstract void onPlayerSelected(Player player);
	
	/**
	 * usually used by skills and equipment for effects
	 * @param card
	 */
	public abstract void onCardSelected(Card card);
}
