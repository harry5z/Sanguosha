package core.client;

import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import core.client.game.operations.Operation;
import core.heroes.Hero;
import net.Channel;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.ClientGameUI;

public interface GamePanel<T extends Hero> extends ClientPanel<ClientGameUI<T>> {

	public void pushOperation(Operation operation, Activatable source);
	
	default public void pushOperation(Operation operation) {
		pushOperation(operation, null);
	}

	public void popOperation();
	
	public void registerEventListener(ClientEventListener<? extends ClientGameEvent, T> listener);
	
	public void removeEventListener(ClientEventListener<? extends ClientGameEvent, T> listener);
	
	public <E extends ClientGameEvent> void emit(E event);

	public Operation getCurrentOperation();

	public Channel getChannel();
	
}
