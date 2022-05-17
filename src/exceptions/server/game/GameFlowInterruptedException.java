package exceptions.server.game;

import commands.game.client.GameClientCommand;

/**
 * 
 * In a {@link core.server.game.controllers.GameController GameController}, there could be multiple steps where the game requires player input (or timeout).
 * For example, in {@link core.server.game.controllers.mechanics.AttackGameController AttackGameController}, there may be source player weapon abilities, the target
 * player may use Dodge, and these steps all require player actions.
 * 
 * In this case, we would need to throw a GameFlowInterruptedException to stop the automatic flow of a GameController, and provide a callback for the controller
 * to execute the intended flow (in most cases, sending some client commands to adjust client UI)
 * 
 * @author Harry
 *
 */
public class GameFlowInterruptedException extends Exception {
	
	private static final long serialVersionUID = -1L;
	
	private final GameClientCommand command;
	
	public GameFlowInterruptedException(GameClientCommand command) {
		this.command = command;
	}
	
	/**
	 * 
	 * @return the command to be sent to all players, or null if no command
	 */
	public GameClientCommand getCommand() {
		return command;
	}
	
}
