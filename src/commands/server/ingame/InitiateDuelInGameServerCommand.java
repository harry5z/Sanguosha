package commands.server.ingame;

import cards.Card;
import cards.specials.instant.Duel;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.DuelGameController;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class InitiateDuelInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	public InitiateDuelInGameServerCommand(PlayerInfo target, Card card) {
		super(target, card);
	}

	@Override
	protected GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		return new DuelGameController(source, target);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof Duel)) {
			throw new IllegalPlayerActionException("Duel: Card is not a Duel");
		}		
	}

	@Override
	protected void validateTarget(GameInternal game) throws IllegalPlayerActionException {
		if (card == null || target == null) {
			throw new IllegalPlayerActionException("Duel: Card/Targets cannot be null");
		}
		
		try {
			card = (Duel) game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Duel: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Duel: Card is not a Duel");
		}

		if (!source.getCardsOnHand().contains(card)) {
			throw new IllegalPlayerActionException("Duel: Player does not own the card used");
		}
		
		PlayerCompleteServer other = game.findPlayer(target);
		if (other == null) {
			throw new IllegalPlayerActionException("Duel: Target not found");
		}
		if (source.equals(other)) {
			throw new IllegalPlayerActionException("Duel: Target cannot be oneself");
		}
		if (!other.isAlive()) {
			throw new IllegalPlayerActionException("Duel: Target not alive");
		}
	}

}
