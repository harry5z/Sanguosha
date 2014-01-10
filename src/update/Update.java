package update;

import java.io.Serializable;

import core.Framework;
import player.*;

/**
 * The only recognized communication class over network, all interactions must be done
 * through this interface
 * @author Harry
 *
 */
public interface Update extends Serializable
{
	/**
	 * behavior of master-side operation
	 * @param framework
	 */
	public void frameworkOperation(Framework framework);
	/**
	 * behavior of client-side operation
	 * @param player
	 */
	public void playerOperation(PlayerOriginalClientComplete player);
	

}
