package commands.server.ingame;

import cards.Card;
import cards.specials.instant.Creation;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.CreationGameController;
import exceptions.server.game.IllegalPlayerActionException;

public class InitiateCreationInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = -1L;

	public InitiateCreationInGameServerCommand(Card card) {
		super(null, card);
	}

	@Override
	protected GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		return new CreationGameController(source);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof Creation)) {
			throw new IllegalPlayerActionException("Creation: Card is not a Creation");
		}
	}

	@Override
	protected void validateTarget(GameInternal game) throws IllegalPlayerActionException {
		
	}

}
