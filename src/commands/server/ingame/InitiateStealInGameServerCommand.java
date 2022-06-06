package commands.server.ingame;

import cards.Card;
import cards.specials.instant.Steal;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.StealGameController;
import exceptions.server.game.IllegalPlayerActionException;

public class InitiateStealInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	public InitiateStealInGameServerCommand(PlayerInfo target, Card card) {
		super(target, card);
	}

	@Override
	protected GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		return new StealGameController(game.getCurrentPlayer(), target);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof Steal)) {
			throw new IllegalPlayerActionException("Steal: Card is not a Steal");
		}		
	}

	@Override
	protected void validateTarget(GameInternal game) throws IllegalPlayerActionException {
		PlayerCompleteServer other = game.findPlayer(target);
		if (other == null) {
			throw new IllegalPlayerActionException("Steal: Target not found");
		}
		if (source.equals(other)) {
			throw new IllegalPlayerActionException("Steal: Target cannot be oneself");
		}
		if (!source.isPlayerInDistance(other, game.getNumberOfPlayersAlive())) {
			throw new IllegalPlayerActionException("Steal: Target is not in distance");
		}
		if (!other.isAlive()) {
			throw new IllegalPlayerActionException("Steal: Target not alive");
		}
		if (!(other.isEquipped() || other.getHandCount() > 0 || other.getDelayedQueue().size() > 0)) {
			throw new IllegalPlayerActionException("Steal: Target has no card to steal");
		}
		
	}

}
