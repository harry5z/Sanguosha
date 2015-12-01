package commands.room;

import commands.ServerCommand;
import core.server.Room;

/**
 * This interface represents {@linkplain ServerCommand}s that will be executed
 * on a {@linkplain Room} on server side.
 * 
 * @author Harry
 *
 */
public interface RoomServerCommand extends ServerCommand<Room> {}
