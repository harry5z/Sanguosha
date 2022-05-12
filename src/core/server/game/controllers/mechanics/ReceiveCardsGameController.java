package core.server.game.controllers.mechanics;

import java.util.Collection;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public class ReceiveCardsGameController extends AbstractGameController<ReceiveCardsGameController.ReceiveCardStage> {

	public static enum ReceiveCardStage implements GameControllerStage<ReceiveCardStage> {
		ADD_CARDS,
		END,
	}
	
	private PlayerCompleteServer target;
	private Collection<Card> cards;
	
	public ReceiveCardsGameController(PlayerCompleteServer target, Collection<Card> cards) {
		this.target = target;
		this.cards = cards;
	}

	@Override
	protected void handleStage(Game game, ReceiveCardStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case ADD_CARDS:
				this.nextStage();
				this.target.addCards(this.cards);
				break;
			case END:
				break;
		}

	}

	@Override
	protected ReceiveCardStage getInitialStage() {
		return ReceiveCardStage.ADD_CARDS;
	}

}
