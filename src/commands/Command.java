package commands;

import java.io.Serializable;

import net.Connection;

/**
 * The only recognized communication class over network, all interactions must
 * be done through this interface
 * 
 * @author Harry
 *
 */
public interface Command<T> extends Serializable {
	/**
	 * The general method for command callback
	 * 
	 * @param listener : an object that command will execute on
	 * @param connection : connection that sends the command
	 */
	public void execute(T object, Connection connection);
	
}
