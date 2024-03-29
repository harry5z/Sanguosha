package commands.client.game.sync;

import commands.client.ClientCommand;

/**
 * This represents a command that the server sends to a client while in game.
 * The command syncs the server side player status with client, which sometimes 
 * triggers a UI update, or performs a custom UI update.
 * 
 * @author Harry
 *
 */
public interface SyncGameClientCommand extends ClientCommand {

}
