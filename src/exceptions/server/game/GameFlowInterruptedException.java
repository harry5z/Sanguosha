package exceptions.server.game;

/**
 * 
 * In a {@link core.server.game.controllers.GameController GameController}, there could be multiple steps where the game requires player input (or timeout).
 * For example, in {@link core.server.game.controllers.AttackGameController AttackGameController}, there may be source player weapon abilities, the target
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
	
	private final Runnable callback;
	
	public GameFlowInterruptedException() {
		this.callback = () -> {};
	}
	
	public GameFlowInterruptedException(Runnable callback) {
		this.callback = callback;
	}
	
	public void resume() {
		this.callback.run();
	}

}
