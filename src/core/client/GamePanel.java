package core.client;

import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import core.client.game.operations.Operation;
import net.Channel;
import ui.game.interfaces.Activatable;

public interface GamePanel extends ClientPanel {
	
	public void pushOperation(Operation operation, Activatable source);

	default public void pushOperation(Operation operation) {
		this.pushOperation(operation, null);
	}
	
	public void popOperation();
	
	public void registerEventListener(ClientEventListener<? extends ClientGameEvent> listener);
	
	public void removeEventListener(ClientEventListener<? extends ClientGameEvent> listener);
	
	public <T extends ClientGameEvent> void emit(T event);

	public Operation getCurrentOperation();

	public Channel getChannel();
	
}
