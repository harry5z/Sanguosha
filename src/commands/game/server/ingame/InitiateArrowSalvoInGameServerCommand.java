package commands.game.server.ingame;

import java.util.Queue;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.ArrowSalvoGameController;

public class InitiateArrowSalvoInGameServerCommand extends AbstractAOEInstantInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;

	public InitiateArrowSalvoInGameServerCommand(Card card) {
		super(card);
	}

	@Override
	protected GameController getAOEInitiationGameController(PlayerCompleteServer self, Queue<PlayerCompleteServer> others) {
		return new ArrowSalvoGameController(self, others);
	}

}
