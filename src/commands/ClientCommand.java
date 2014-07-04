package commands;

import net.client.Client;

/**
 * This is the general interface for commands that are
 * executed on a {@linkplain Client} over the client side. 
 * 
 * @author Harry
 *
 */
public interface ClientCommand extends Command<Client> {}
