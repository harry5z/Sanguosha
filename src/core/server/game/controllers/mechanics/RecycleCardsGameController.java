package core.server.game.controllers.mechanics;

import java.util.Collection;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import utils.EnumWithNextStage;

public class RecycleCardsGameController extends AbstractGameController {

	public static enum RecycleCardStage implements EnumWithNextStage<RecycleCardStage> {
		RECYCLE_CARDS,
		END,
	}
	
	private PlayerCompleteServer target;
	private Collection<Card> cards;
	private RecycleCardStage stage;
	
	public RecycleCardsGameController(Game game, PlayerCompleteServer target, Collection<Card> cards) {
		super(game);
		this.target = target;
		this.cards = cards;
		this.stage = RecycleCardStage.RECYCLE_CARDS;
	}

	@Override
	public void proceed() {
		switch(this.stage) {
			case RECYCLE_CARDS:
				for (Card card : this.cards) {
					this.target.getDisposalListener().onCardDisposed(card);
				}
				this.stage = this.stage.nextStage();
				this.proceed();
				break;
			case END:
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}

	}

}
