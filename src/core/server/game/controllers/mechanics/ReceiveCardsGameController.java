package core.server.game.controllers.mechanics;

import java.util.Collection;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import utils.EnumWithNextStage;

public class ReceiveCardsGameController extends AbstractGameController {

	public static enum ReceiveCardStage implements EnumWithNextStage<ReceiveCardStage> {
		ADD_CARDS,
		END,
	}
	
	private PlayerCompleteServer target;
	private Collection<Card> cards;
	private ReceiveCardStage stage;
	
	public ReceiveCardsGameController(Game game, PlayerCompleteServer target, Collection<Card> cards) {
		super(game);
		this.target = target;
		this.cards = cards;
		this.stage = ReceiveCardStage.ADD_CARDS;
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

}
