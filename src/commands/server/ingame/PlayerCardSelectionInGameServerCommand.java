package commands.server.ingame;

import cards.Card;
import core.player.PlayerCardZone;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class PlayerCardSelectionInGameServerCommand extends InGameServerCommand {
	
	private static final long serialVersionUID = 1L;
	
	private Card card;
	private final PlayerCardZone zone;
	
	public PlayerCardSelectionInGameServerCommand(Card card, PlayerCardZone zone) {
		this.card = card;
		this.zone = zone;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.<CardSelectableGameController>getNextGameController().onCardSelected(game, card, zone);
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		try {
			card = game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			card = null;
		}
		game.<CardSelectableGameController>getCurrentGameController().validateCardSelected(game, card, zone);
	}

}
