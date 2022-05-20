package commands.game.server.ingame;

import java.util.LinkedList;
import java.util.Queue;

import cards.Card;
import cards.specials.instant.Harvest;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.HarvestGameController;
import exceptions.server.game.IllegalPlayerActionException;

public class InitiateHarvestInGameServerCommand extends AbstractAOEInstantInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;

	public InitiateHarvestInGameServerCommand(Card card) {
		super(card);
	}

	@Override
	protected GameController getAOEInitiationGameController(PlayerCompleteServer self, Queue<PlayerCompleteServer> others) {
		Queue<PlayerCompleteServer> targets = new LinkedList<>();
		targets.add(self);
		targets.addAll(others);
		return new HarvestGameController(self, targets);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof Harvest)) {
			throw new IllegalPlayerActionException("Harvest: Card is not a Harvest");
		}
	}

}
