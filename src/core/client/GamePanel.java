package core.client;

import java.util.UUID;

import commands.game.server.ingame.InGameServerCommand;
import core.GameState;
import core.client.game.event.ClientGameEvent;
import core.client.game.listener.ClientEventListener;
import core.client.game.operations.Operation;
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
	
	/**
	 * Push a new Operation into the game execution stack.
	 * The existing Operation's #onUnloaded() will be called.
	 * 
	 * @param operation
	 */
	public void pushOperation(Operation operation);
	
	/**
	 * Call by a server command to push a new Operation into 
	 * the game execution stack. The only difference between
	 * this method and {@link #pushOperation(Operation)} is that 
	 * this method also sets a countdown timer (based on game config) 
	 * which, upon timeout, calls #onUnloaded() and #onDeactivated() 
	 * on the current operation
	 * 
	 * @param operation
	 */
	public void pushPlayerActionOperation(Operation operation);

	public void popOperation();
	
	public void registerEventListener(ClientEventListener<? extends ClientGameEvent> listener);
	
	public void removeEventListener(ClientEventListener<? extends ClientGameEvent> listener);
	
	public <T extends ClientGameEvent> void emit(T event);

	public Operation getCurrentOperation();

	public GameUI getGameUI();
	
	public GameState getGameState();
	
	public void setNextResponseID(UUID id);
	
	public void sendResponse(InGameServerCommand command);

}
