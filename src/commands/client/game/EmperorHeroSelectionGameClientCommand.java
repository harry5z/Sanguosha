package commands.client.game;

import java.util.List;
import java.util.Set;

import commands.server.ingame.HeroSelectionInGameServerCommand;
import commands.server.ingame.HeroSelectionTimeoutInGameServerCommand;
import commands.server.ingame.InGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.mechanics.HeroSelectionOperation;
import core.heroes.Hero;
import core.player.PlayerCompleteServer;

public class EmperorHeroSelectionGameClientCommand extends AbstractSingleTargetOperationGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final List<Hero> availableHeroes;

	public EmperorHeroSelectionGameClientCommand(PlayerCompleteServer target, List<Hero> availableHeroes) {
		super(target.getPlayerInfo());
		this.availableHeroes = availableHeroes;
		this.timeoutMS = 30000; // allow each player to spend 30s on hero selection
	}
	
	@Override
	protected boolean shouldClearGamePanel() {
		return false;
	}

	@Override
	protected Operation getOperation() {
		return new HeroSelectionOperation(availableHeroes);
	}

	@Override
	public Set<Class<? extends InGameServerCommand>> getAllowedResponseTypes() {
		return Set.of(HeroSelectionInGameServerCommand.class);
	}

	@Override
	public InGameServerCommand getDefaultResponse() {
		return new HeroSelectionTimeoutInGameServerCommand();
	}
	
	@Override
	public void setResponseTimeoutMS(int timeMS) {
		// disable changing timeout
	}
	
	@Override
	protected String getMessageForOthers() {
		return "Waiting on " + target.getName() + " to select a hero";
	}
	
}
