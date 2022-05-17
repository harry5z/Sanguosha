package commands.game.client.sync;

import commands.Command;
import core.client.ClientFrame;

/**
 * This represents a command that the server sends to a client while in game.
 * The command performs only a UI update.
 * 
 * @author Harry
 *
 */
public interface SyncGameUIClientCommand extends Command<ClientFrame> {

}
