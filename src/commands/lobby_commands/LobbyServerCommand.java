package commands.lobby_commands;

import net.server.Lobby;

import commands.ServerCommand;


/**
 * This interface represents {@linkplain ServerCommand}s that will be executed
 * on a {@linkplain Lobby} on server side.
 * 
 * @author Harry
 *
 */
public interface LobbyServerCommand extends ServerCommand<Lobby> {}
