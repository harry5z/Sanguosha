package core.server.game.controllers.mechanics;

import java.util.Collection;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public class RecycleCardsGameController extends AbstractGameController<RecycleCardsGameController.RecycleCardStage> {

	public static enum RecycleCardStage implements GameControllerStage<RecycleCardStage> {
		RECYCLE_CARDS,
		END,
	}
	
	private PlayerCompleteServer target;
	private Collection<Card> cards;
	
	public RecycleCardsGameController(PlayerCompleteServer target, Collection<Card> cards) {
		this.target = target;
		this.cards = cards;
	}

	@Override
	protected void handleStage(Game game, RecycleCardStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case RECYCLE_CARDS:
				for (Card card : this.cards) {
					this.target.getDisposalListener().onCardDisposed(card);
				}
				this.nextStage();
				break;
			case END:
				break;
		}

	}

	@Override
	protected RecycleCardStage getInitialStage() {
		return RecycleCardStage.RECYCLE_CARDS;
	}

}
