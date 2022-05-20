package commands.game.server.ingame;

import cards.Card;
import cards.basics.Dodge;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.DodgeGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class DodgeReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 8547546299242633692L;

	private Card dodge;
	
	/**
	 * A regular Dodge reaction to respond to Attack, Arrow Salvo, etc.
	 * The card used must be on a player's hand, and must be an Dodge card. If card is null, 
	 * The player gives up reaction
	 * 
	 * @param card : An Dodge card from player's hand, or null which indicates inaction
	 */
	public DodgeReactionInGameServerCommand(Card card) {
		this.dodge = card;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				if (dodge != null) {
					game.<DodgeGameController>getNextGameController().onDodgeUsed(game, dodge);
				} else {
					game.<DodgeGameController>getNextGameController().onDodgeNotUsed();
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (dodge == null) {
			return;
		}
		try {
			dodge = game.getDeck().getValidatedCard(dodge);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Dodge Reaction: Card is invalid");
		}

		if (!(dodge instanceof Dodge)) {
			throw new IllegalPlayerActionException("Dodge Reaction: Card is not an Dodge");
		}
		if (!source.getCardsOnHand().contains(dodge)) {
			throw new IllegalPlayerActionException("Dodge Reaction: Player does not own the card used");
		}		
	}
	
}
