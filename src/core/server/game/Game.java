package core.server.game;

public interface Game extends GameInternal {
	
	/**
	 * start the game
	 */
	public void start();

	public void resume();

}
