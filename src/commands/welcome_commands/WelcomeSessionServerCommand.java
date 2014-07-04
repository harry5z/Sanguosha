package commands.welcome_commands;

import net.server.WelcomeSession;
import commands.ServerCommand;

/**
 * This interface represents {@linkplain ServerCommand}s that will be executed
 * on a {@linkplain WelcomeSession} on server side.
 * 
 * @author Harry
 *
 */
public interface WelcomeSessionServerCommand extends ServerCommand<WelcomeSession> {}
