package utils;

/**
 * Simple logger
 * @author Harry
 *
 */
public class Log {
	
	private Log() {} // not to be initialized
	
	/**
	 * Prints error log
	 * @param tag : sender of message
	 * @param message : text of message
	 */
	public static void e(String tag, String message) {
		System.err.println("[ERROR] " + tag + ": " + message);
	}
	
	/**
	 * Log a message
	 * @param tag : sender of message
	 * @param message : text of message
	 */
	public static void log(String tag, String message) {
		System.out.println("[LOG] " + tag + ": " + message);
	}
}
