package commands.game.server.ingame;

import cards.Card;
import cards.specials.instant.FireAttack;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.FireAttackController;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class InitiateFireAttackInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;

	public InitiateFireAttackInGameServerCommand(PlayerInfo target, Card card) {
		super(target, card);
	}

	@Override
	protected GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		return new FireAttackController(source, target);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (!(card instanceof FireAttack)) {
			throw new IllegalPlayerActionException("Fire Attack: Card is not a FireAttack");
		}
	}

	@Override
	protected void validateTarget(GameInternal game) throws IllegalPlayerActionException {
		if (card == null || target == null) {
			throw new IllegalPlayerActionException("Fire Attack: Card/Target cannot be null");
		}
		
		try {
			card = (FireAttack) game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Fire Attack: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Fire Attack: Card is not a FireAttack");
		}

		if (!source.getCardsOnHand().contains(card)) {
			throw new IllegalPlayerActionException("Fire Attack: Player does not own the card used");
		}
		
		PlayerCompleteServer other = game.findPlayer(target);
		if (other == null) {
			throw new IllegalPlayerActionException("Fire Attack: Target not found");
		}
		if (other.getHandCount() == 0) {
			throw new IllegalPlayerActionException("Fire Attack: Target must have cards on hand");
		}
		if (!other.isAlive()) {
			throw new IllegalPlayerActionException("Fire Attack: Target not alive");
		}
	}

}
