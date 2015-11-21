package exceptions.server.game;

public class InvalidPlayerCommandException extends Exception {
	
	private static final long serialVersionUID = -776020891376048861L;

	public InvalidPlayerCommandException(String message) {
		super(message);
	}

}
