package commands;

import net.Connection;
import net.client.Client;
import net.client.ClientUI;

/**
 * This is the general interface for {@linkplain ClientCommand}s
 * that updates client's ui. <br><br>
 * 
 * <strong>Note</strong>: Only override {@link UIClientCommand#execute(ClientUI, Connection)}
 * 
 * @author Harry
 *
 */
public interface UIClientCommand extends Command<Client> {

	/**
	 * <strong>IMPORTANT: Do NOT override this method</strong><br><br>
	 * {@inheritDoc}
	 */
	@Override
	default public void execute(Client client, Connection connection) {
		this.execute(client.getClientListener(), connection);
	}
	
	/**
	 * Execute the command on client's ui
	 * 
	 * @param ui : client's ui (as {@linkplain ClientUI})
	 * @param connection : the connection that sends the command
	 * 
	 * @see ClientCommand#execute(Client, Connection)
	 */
	public abstract void execute(ClientUI ui, Connection connection);
}
