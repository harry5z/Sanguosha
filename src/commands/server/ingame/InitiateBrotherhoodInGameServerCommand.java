package commands.server.ingame;

import java.util.LinkedList;
import java.util.Queue;

import cards.Card;
import cards.specials.instant.Brotherhood;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.BrotherhoodGameController;
import exceptions.server.game.IllegalPlayerActionException;

public class InitiateBrotherhoodInGameServerCommand extends AbstractAOEInstantInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	public InitiateBrotherhoodInGameServerCommand(Card card) {
		super(card);
	}
	
	@Override
	protected GameController getAOEInitiationGameController(PlayerCompleteServer self, Queue<PlayerCompleteServer> others) {
		Queue<PlayerCompleteServer> queue = new LinkedList<>();
		queue.add(self);
		queue.addAll(others);
		return new BrotherhoodGameController(self, queue);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof Brotherhood)) {
			throw new IllegalPlayerActionException("Brotherhood: Card is not a Brotherhood");
		}		
	}

}
