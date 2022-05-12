package core.server.game.controllers.specials.delayed;

import cards.Card;
import cards.Card.Suit;
import core.event.handlers.turn.SkipDrawTurnEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.mechanics.TurnGameController;
import utils.DelayedStackItem;
import utils.DelayedType;

public class StarvationArbitrationController extends AbstractDelayedArbitrationController {

	public StarvationArbitrationController(PlayerCompleteServer target, TurnGameController turn) {
		super(target, turn);
	}

	@Override
	protected void handleEffect(Game game) {
		game.registerEventHandler(new SkipDrawTurnEventHandler(this.target));
		DelayedStackItem item = this.target.removeDelayed(DelayedType.STARVATION);
		game.getDeck().discard(item.delayed);
	}
	
	@Override
	protected String getNeutralizationMessage() {
		return this.target + " will start arbitration for Starvation, use Neutralization?";
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
