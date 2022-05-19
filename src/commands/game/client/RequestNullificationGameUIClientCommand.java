package commands.game.client;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.NullificationReactionInGameServerCommand;
import commands.game.server.ingame.NullificationTimeoutInGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.instants.NullificationOperation;
import core.heroes.skills.ActiveSkill;
import core.heroes.skills.Skill;
import core.player.PlayerCompleteServer;

public class RequestNullificationGameUIClientCommand extends AbstractPlayerActionGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	private UUID uuid;
	private final transient Collection<PlayerCompleteServer> players;
	
	public RequestNullificationGameUIClientCommand(String message, Collection<PlayerCompleteServer> players) {
		this.message = message;
		this.players = players;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		// response ID must be present for the response to be accepted by server
		panel.setNextResponseID(uuid);
		panel.pushPlayerActionOperation(new NullificationOperation(this.message), timeoutMS);
		panel.getGameUI().getOtherPlayersUI().forEach(ui -> ui.showCountdownBar(timeoutMS));
	}

	@Override
	public UUID generateResponseID(String name) {
		uuid = UUID.randomUUID(); // anyone can respond to Nullification
		return uuid;
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		Set<Class<? extends InGameServerCommand>> types = new HashSet<>();
		// allow regular Nullification reaction
		types.add(NullificationReactionInGameServerCommand.class);
		
		for (PlayerCompleteServer player : players) {
			for (Skill skill : player.getHero().getSkills()) {
				if (skill instanceof ActiveSkill) {
					Class<? extends InGameServerCommand> type = ((ActiveSkill) skill).getAllowedResponseType(this);
					if (type != null) {
						types.add(type);
					}
				}
			}
		}
		return types;
	}

	@Override
	public InGameServerCommand getDefaultResponse() {
		// Note that Nullification Timeout response is not an allowed response type.
		// This can only be used by the server internally
		return new NullificationTimeoutInGameServerCommand();
	}

}
