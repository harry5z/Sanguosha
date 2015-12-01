package core.client;

import core.client.game.operations.Operation;
import core.heroes.Hero;
import net.Channel;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.ClientGameUI;

public interface GamePanel<T extends Hero> extends ClientPanel<ClientGameUI<T>> {

	public void pushOperation(Operation operation, Activatable source);

	public void popOperation();

	public Operation getCurrentOperation();

	public Channel getChannel();
	
}
