package commands.game.client;

import commands.Command;
import core.client.ClientFrame;
import core.heroes.Hero;

public interface GameClientCommand<T extends Hero> extends Command<ClientFrame> {

}
