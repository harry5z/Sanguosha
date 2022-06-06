package commands.server.ingame;

import java.util.Queue;

import cards.Card;
import cards.specials.instant.ArrowSalvo;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.ArrowSalvoGameController;
import exceptions.server.game.IllegalPlayerActionException;

public class InitiateArrowSalvoInGameServerCommand extends AbstractAOEInstantInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;

	public InitiateArrowSalvoInGameServerCommand(Card card) {
		super(card);
	}

	@Override
	protected GameController getAOEInitiationGameController(PlayerCompleteServer self, Queue<PlayerCompleteServer> others) {
		return new ArrowSalvoGameController(self, others);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof ArrowSalvo)) {
			throw new IllegalPlayerActionException("Arrow Salvo: Card is not an Arrow Salvo");
		}
	}

}
