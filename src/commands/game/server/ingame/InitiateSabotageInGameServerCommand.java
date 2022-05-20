package commands.game.server.ingame;

import cards.Card;
import cards.specials.instant.Sabotage;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.SabotageGameController;
import exceptions.server.game.IllegalPlayerActionException;

public class InitiateSabotageInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	public InitiateSabotageInGameServerCommand(PlayerInfo target, Card card) {
		super(target, card);
	}

	@Override
	protected GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		return new SabotageGameController(game.getCurrentPlayer(), target);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof Sabotage)) {
			throw new IllegalPlayerActionException("Sabotage: Card is not a Sabotage");
		}
	}

	@Override
	protected void validateTarget(GameInternal game) throws IllegalPlayerActionException {
		PlayerCompleteServer other = game.findPlayer(target);
		if (other == null) {
			throw new IllegalPlayerActionException("Sabotage: Target not found");
		}
		if (source.equals(other)) {
			throw new IllegalPlayerActionException("Sabotage: Target cannot be oneself");
		}
		if (!other.isAlive()) {
			throw new IllegalPlayerActionException("Sabotage: Target not alive");
		}
		if (!(other.isEquipped() || other.getHandCount() > 0 || other.getDelayedQueue().size() > 0)) {
			throw new IllegalPlayerActionException("Sabotage: Target has no card to discard");
		}
	}

}
