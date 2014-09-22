package commands.room;

import net.server.Room;

import commands.ServerCommand;

/**
 * This interface represents {@linkplain ServerCommand}s that will be executed
 * on a {@linkplain Room} on server side.
 * 
 * @author Harry
 *
 */
public interface RoomServerCommand extends ServerCommand<Room> {}
