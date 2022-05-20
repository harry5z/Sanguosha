package exceptions.server.game;

@SuppressWarnings("serial")
public class InvalidCardException extends Exception {

	public InvalidCardException(String message) {
		super(message);
	}
}
