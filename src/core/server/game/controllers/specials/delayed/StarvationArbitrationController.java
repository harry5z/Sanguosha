package core.server.game.controllers.specials.delayed;

import cards.Card;
import cards.Card.Suit;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.mechanics.TurnGameController;
import core.server.game.controllers.mechanics.TurnGameController.TurnStage;
import utils.DelayedStackItem;
import utils.DelayedType;

public class StarvationArbitrationController extends AbstractDelayedArbitrationController {

	public StarvationArbitrationController(PlayerCompleteServer target, TurnGameController turn) {
		super(target, turn);
	}

	@Override
	protected void handleEffect(Game game) {
		// skip DRAW
		this.currentTurn.skipStage(TurnStage.DRAW);
		DelayedStackItem item = this.target.removeDelayed(DelayedType.STARVATION);
		game.getDeck().discard(item.delayed);
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.target + " will start arbitration for Starvation, use Nullification?";
	}

	@Override
	protected void beforeEnd(Game game) {
		// if Starvation is not effective, discard it
		if (this.target.hasDelayedType(DelayedType.STARVATION)) {
			DelayedStackItem item = this.target.removeDelayed(DelayedType.STARVATION);
			game.getDeck().discard(item.delayed);
		}
	}

	@Override
	protected boolean isArbitrationEffective(Card card) {
		return card.getSuit() != Suit.CLUB;
	}

}
