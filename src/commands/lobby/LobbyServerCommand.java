package commands.lobby;

import commands.ServerCommand;
import core.server.Lobby;


/**
 * This interface represents {@linkplain ServerCommand}s that will be executed
 * on a {@linkplain Lobby} on server side.
 * 
 * @author Harry
 *
 */
public interface LobbyServerCommand extends ServerCommand<Lobby> {}
