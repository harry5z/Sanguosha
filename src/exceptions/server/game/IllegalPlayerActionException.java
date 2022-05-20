package exceptions.server.game;

@SuppressWarnings("serial")
public class IllegalPlayerActionException extends Exception {

	public IllegalPlayerActionException(String message) {
		super(message);
	}
}
