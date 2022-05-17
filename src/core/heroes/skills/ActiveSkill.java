package core.heroes.skills;

import commands.game.client.GameClientCommand;
import commands.game.server.ingame.InGameServerCommand;

/**
 * 
 * Active skills are skills which require either player confirmation or player action,
 * or both. The activation of active skills is optional, as the player can choose whether
 * or not to use a certain active skill when the triggering conditions are met.
 * 
 * @author Harry
 *
 */
public interface ActiveSkill extends OriginalHeroSkill {
	
	/**
	 * Get the allowed player action type based on the outgoing command type
	 * 
	 * @param command : outgoing command
	 * @return type of allowed player action, or null if no action is allowed
	 */
	public Class<? extends InGameServerCommand> getAllowedResponseType(GameClientCommand command);

}
