package commands.server.ingame;

import java.util.Queue;

import cards.Card;
import cards.specials.instant.BarbarianInvasion;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.BarbarianInvasionGameController;
import exceptions.server.game.IllegalPlayerActionException;

public class InitiateBarbarianInvasionInGameServerCommand extends AbstractAOEInstantInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	public InitiateBarbarianInvasionInGameServerCommand(Card card) {
		super(card);
	}

	@Override
	protected GameController getAOEInitiationGameController(PlayerCompleteServer self, Queue<PlayerCompleteServer> others) {
		return new BarbarianInvasionGameController(self, others);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof BarbarianInvasion)) {
			throw new IllegalPlayerActionException("Barbarian Invasion: Card is not a BarbarianInvasion");
		}
	}

}
