package verifiers;

import commands.Command;

/**
 * Server side utility that verifies that a command
 * sent by client is a legal one.
 * 
 * @author Harry
 *
 * @param <T>
 */
public interface Verifier<T extends Command<?>> {
	
	/**
	 * Verify a command 
	 * 
	 * @param command : command to be verified
	 * 
	 * @return true on success, false on reject
	 */
	public boolean verify(T command);
	
}
