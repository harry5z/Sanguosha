package commands.game.server.ingame;

import java.util.List;

import cards.Card;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class DiscardInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = -2110615050430784494L;

	private final List<Card> cards;
	
	public DiscardInGameServerCommand(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				try {
					// TODO convert to discard controller
					game.getCurrentPlayer().discardCards(cards);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
					return;
				}
			}
		};
	}

}
