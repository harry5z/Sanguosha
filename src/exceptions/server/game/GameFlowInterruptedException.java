package exceptions.server.game;

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
