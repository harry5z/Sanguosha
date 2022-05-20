package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public abstract class AbstractInitiationInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	protected final PlayerInfo target;
	protected Card card;

	/**
	 * 
	 * @param target : target player, or null if self-targeted or AOE
	 * @param card : card used for this initiation, which may not be null
	 */
	public AbstractInitiationInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
		this.card = card;
	}

	@Override
	public final GameController getGameController() {
		return new AbstractSingleStageGameController() {
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.pushGameController(getInitiationGameController(game, target != null ? game.findPlayer(target) : null));
				game.pushGameController(new UseCardOnHandGameController(source, Set.of(card)));			
			}
		};
	}
	
	@Override
	public final void validate(GameInternal game) throws IllegalPlayerActionException {
		if (card == null) {
			throw new IllegalPlayerActionException("Initiation: Card cannot be null");
		}
		
		try {
			card = game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Initiation: Card is invalid");
		}

		if (!source.getCardsOnHand().contains(card)) {
			throw new IllegalPlayerActionException("Initiation: Player does not own the card used");
		}
		
		validateCardType(game);
		validateTarget(game);
	}
	
	protected abstract void validateCardType(GameInternal game) throws IllegalPlayerActionException;
	
	protected abstract void validateTarget(GameInternal game) throws IllegalPlayerActionException;
	
	protected abstract GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target);

}
