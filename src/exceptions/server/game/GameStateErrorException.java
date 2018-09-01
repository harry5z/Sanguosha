package exceptions.server.game;

public class GameStateErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public GameStateErrorException(String message) {
		super(message);
	}

}
