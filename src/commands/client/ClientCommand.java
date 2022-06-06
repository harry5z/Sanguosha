package commands.client;

import commands.Command;
import core.client.ClientFrame;
import net.client.ClientConnection;

public interface ClientCommand extends Command<ClientFrame, ClientConnection> {

}
