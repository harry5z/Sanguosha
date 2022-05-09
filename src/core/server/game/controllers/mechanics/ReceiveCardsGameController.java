package core.server.game.controllers.mechanics;

import java.util.Collection;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;

public class ReceiveCardsGameController extends AbstractGameController<ReceiveCardsGameController.ReceiveCardStage> {

	public static enum ReceiveCardStage implements GameControllerStage<ReceiveCardStage> {
		ADD_CARDS,
		END,
	}
	
	private PlayerCompleteServer target;
	private Collection<Card> cards;
	
	public ReceiveCardsGameController(Game game, PlayerCompleteServer target, Collection<Card> cards) {
		super(game);
		this.target = target;
		this.cards = cards;
	}

	@Override
	public void proceed() {
		switch(this.stage) {
			case ADD_CARDS:
				this.stage = this.stage.nextStage();
				this.target.addCards(this.cards);
				this.proceed();
				break;
			case END:
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}

	}

	@Override
	protected ReceiveCardStage getInitialStage() {
		return ReceiveCardStage.ADD_CARDS;
	}

}
