package commands;

import net.server.ServerEntity;

/**
 * This is the general marker interface for commands that are
 * executed on a {@linkplain ServerEntity} over the server side. 
 * 
 * @author Harry
 *
 */
public interface ServerCommand<T extends ServerEntity> extends Command<T> {}
