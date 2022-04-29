package core.client;

import core.GameState;
import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import core.client.game.operations.Operation;
import net.Channel;
import ui.game.interfaces.GameUI;

/**
 * 
 * The main in-game panel which includes additional functions to run the game,
 * e.g. methods to handle Operation and ClientGameEvent
 * 
 * It also has the game's interface, which can be used to update the UI for the user.
 * It also has the game's state, which can be used to check player status and other info.
 * 
 * @author Harry
 *
 */
public interface GamePanel extends ClientPanel {
	
	public void pushOperation(Operation operation);

	public void popOperation();
	
	public void registerEventListener(ClientEventListener<? extends ClientGameEvent> listener);
	
	public void removeEventListener(ClientEventListener<? extends ClientGameEvent> listener);
	
	public <T extends ClientGameEvent> void emit(T event);

	public Operation getCurrentOperation();

	public Channel getChannel();
	
	public GameUI getGameUI();
	
	public GameState getGameState();

}
