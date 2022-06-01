package commands.game.client;

import java.util.HashSet;
import java.util.Set;

import commands.game.server.ingame.DodgeReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.basics.DodgeReactionOperation;
import core.heroes.skills.ActiveSkill;
import core.heroes.skills.Skill;
import core.player.PlayerCompleteServer;

public class RequestDodgeGameUIClientCommand extends AbstractSingleTargetOperationGameClientCommand {
	
	private static final long serialVersionUID = 1L;

	private final transient PlayerCompleteServer player;
	private final String message;
	
	public RequestDodgeGameUIClientCommand(PlayerCompleteServer target, String message) {
		super(target.getPlayerInfo());
		this.player = target;
		this.message = message;
	}

	@Override
	protected Operation getOperation() {
		return new DodgeReactionOperation(this.message);
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		Set<Class<? extends InGameServerCommand>> types = new HashSet<>();
		// allow regular Dodge reaction (with a Dodge card)
		types.add(DodgeReactionInGameServerCommand.class);
		// allow skills that can turn something into Dodge
		for (Skill skill : player.getHero().getSkills()) {
			if (skill instanceof ActiveSkill) {
				Class<? extends InGameServerCommand> type = ((ActiveSkill) skill).getAllowedResponseType(this);
				if (type != null) {
					types.add(type);
				}
			}
		}
		return types;
	}

	@Override
	public InGameServerCommand getDefaultResponse() {
		return new DodgeReactionInGameServerCommand(null);
	}
	
	@Override
	protected String getMessageForOthers() {
		return "Waiting on " + target.getName() + " to use Dodge";
	}

}
