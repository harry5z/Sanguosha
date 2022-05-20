package commands.game.server.ingame;

import cards.Card;
import cards.Card.Color;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.FireAttackController;
import exceptions.server.game.IllegalPlayerActionException;

public class ZhugeliangInitiateFireAttackInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;

	public ZhugeliangInitiateFireAttackInGameServerCommand(PlayerInfo target, Card card) {
		super(target, card);
	}

	@Override
	protected GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		return new FireAttackController(game.getCurrentPlayer(), target);
	}

	@Override
	protected void validateCardType(GameInternal game) throws IllegalPlayerActionException {
		if (card.getColor() != Color.RED) {
			throw new IllegalPlayerActionException("Fire Attack Skill: Card must be RED");
		}
	}

	@Override
	protected void validateTarget(GameInternal game) throws IllegalPlayerActionException {
		if (target == null) {
			throw new IllegalPlayerActionException("Fire Attack Skill: Target cannot be null");
		}
		
		PlayerCompleteServer other = game.findPlayer(target);
		if (other == null) {
			throw new IllegalPlayerActionException("Fire Attack Skill: Target not found");
		}
		if (other.getHandCount() == 0) {
			throw new IllegalPlayerActionException("Fire Attack Skill: Target must have cards on hand");
		}
		if (!other.isAlive()) {
			throw new IllegalPlayerActionException("Fire Attack Skill: Target not alive");
		}		
	}

}
