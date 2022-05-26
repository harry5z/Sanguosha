package commands.game.client;

import java.util.Set;
import java.util.UUID;

import commands.Command;
import commands.game.server.ingame.InGameServerCommand;
import core.client.ClientFrame;

/**
 * This represents a command that the server sends to a client while in game.
 * The command may be a UI update or it may also allow certain players to
 * post a response (player action, e.g. use Dodge).
 * 
 * <p>If a player is allowed to post a response, 
 * then they will receive a UUID generated on the server side which must be sent back
 * along with the response command so the server may accept the response. Having a UUID
 * prevents players who are not allowed to respond from responding to the current
 * action, either due to network lag (e.g. sending a response from a previous action) or 
 * malicious intent (e.g. attempting to post an action in place of another player)</p>
 * 
 * <p>Each command also include a list of allowed response types. Any response that are not
 * one of the allowed types will be ignored. This prevent players from cheating, i.e. using
 * cards or skills that they could not use at the time</p>
 * 
 * @author Harry
 *
 */
public interface PlayerActionGameClientCommand extends Command<ClientFrame>, Cloneable {
	
	/**
	 * Optionally generate and store a response UUID to be sent to a certain player within this command. 
	 * A response UUID should only be generated if the player is allowed to post a response 
	 * (a player action). In most cases, it would be the target player of this command. If a
	 * player is not expected to respond, a UUID must not be generated
	 * 
	 * <p>Having a UUID prevents players who are not allowed to respond from responding to the current
	 * game state, either due to network lag (e.g. sending a response from a previous state) or 
	 * malicious intent (e.g. attempting to post an action in place of another player)</p>
	 * 
	 * @param name : name of the player
	 * @return null if the player cannot respond, or generated UUID if the player may respond
	 */
	public UUID generateResponseID(String name);
	
	/**
	 * Get the allowed response types for the command. If a reponse of an invalid type is received,
	 * the game will reject the response. This prevent players from cheating, i.e. using
     * cards or skills that they could not use at the time.
     * 
     * @return set of allowed response command types represented by their classes
	 * 
	 */
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes();
	
	/**
	 * Get the default response for a player action. For each action, the player has a limited
	 * amount of time to post a reaction. If no reaction is posted and the action times out, the
	 * default response will be used in place of player response.
	 * 
	 * @return the default response that will be used upon player action timeout
	 */
	public InGameServerCommand getDefaultResponse();
	
	/**
	 * Set a timeout in millisecond for the client to display countdown bar and disable response
	 * when timeout is reached. By default, it should be the timeout set via game config.
	 * 
	 * @param timeMS : timeout in milliseconds
	 */
	public void setResponseTimeoutMS(int timeMS);
	
	/**
	 * Clone the command. Many Player Action commands will be sent to multiple players, with slightly
	 * different content. Thus a separate command is needed for each player
	 * 
	 * @return a shallow copy of the command
	 */
	public PlayerActionGameClientCommand clone();
}
