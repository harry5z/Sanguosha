package commands.client.game;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import commands.server.ingame.HeroSelectionInGameServerCommand;
import commands.server.ingame.HeroSelectionTimeoutInGameServerCommand;
import commands.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.mechanics.HeroSelectionOperation;
import core.heroes.Hero;
import core.player.PlayerCompleteServer;
import core.player.Role;

public class NonEmperorHeroSelectionGameClientCommand extends AbstractPlayerActionGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final transient Map<PlayerCompleteServer, List<Hero>> allowedHeroChoices;

	private List<Hero> availableHeroes;
	private UUID uuid;
	
	public NonEmperorHeroSelectionGameClientCommand(Map<PlayerCompleteServer, List<Hero>> allowedHeroChoices) {
		this.allowedHeroChoices = allowedHeroChoices;
		this.timeoutMS = 30000; // allow each player to spend 30s on hero selection
	}
	
	@Override
	protected void execute(GamePanel panel) {
		if (panel.getGameState().getSelf().getRole() == Role.EMPEROR) {
			// emperor waits for others to complete hero selection
			panel.getGameUI().setMessage("Waiting for others to select heroes");
			panel.getGameUI().getOtherPlayersUI().forEach(ui -> ui.showCountdownBar(timeoutMS));
		} else {
			// response ID must be present for the response to be accepted by server
			panel.setNextResponseID(uuid);	
			panel.pushPlayerActionOperation(new HeroSelectionOperation(availableHeroes), timeoutMS);
			panel.getGameUI().getOtherPlayersUI().forEach(ui -> {
				if (ui.getPlayer().getRole() != Role.EMPEROR) { // emperor has already selected hero
					ui.showCountdownBar(timeoutMS);
				}
			});
		}
	}

	@Override
	public UUID generateResponseID(String name) {
		for (Map.Entry<PlayerCompleteServer, List<Hero>> entry : allowedHeroChoices.entrySet()) {
			if (entry.getKey().getName().equals(name)) {
				uuid = UUID.randomUUID();
				// WARNING: Hacky, correct execution depends on that this method is called for each player
				// TODO make multi-target player action command less hacky
				availableHeroes = entry.getValue();
				return uuid;
			}
		}
		uuid = null;
		return null;
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

}
