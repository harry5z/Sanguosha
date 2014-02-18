package update;

import java.io.Serializable;

import player.PlayerOriginalClientComplete;
import core.Framework;

/**
 * The only recognized communication class over network, all interactions must be done
 * through this interface
 * @author Harry
 *
 */
public abstract class Update implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5280172720560777109L;
	private Update next;
	
	/**
	 * create a new update with the next update set
	 * @param next
	 */
	public Update(Update next)
	{
		this.next = next;
	}
	/**
	 * behavior of master-side operation
	 * @param framework
	 */
	public abstract void frameworkOperation(Framework framework);
	/**
	 * behavior of client-side operation
	 * @param player
	 */
	public abstract void playerOperation(PlayerOriginalClientComplete player);
	
	/**
	 * get the next update
	 * @return next
	 */
	public Update getNext()
	{
		return next;
	}
	
	/**
	 * set the next update
	 * @param update
	 */
	public void setNext(Update update)
	{
		next = update;
	}
	
	/**
	 * insert a new update before the next update
	 * @param update : update to be inserted
	 */
	public void insertNext(Update update)
	{
		update.setNext(next);
		next = update;
	}
}
