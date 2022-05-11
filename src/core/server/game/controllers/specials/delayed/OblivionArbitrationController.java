package core.server.game.controllers.specials.delayed;

import cards.Card;
import cards.Card.Suit;
import core.event.handlers.turn.SkipDealTurnEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.mechanics.TurnGameController;
import utils.DelayedStackItem;
import utils.DelayedType;

public class OblivionArbitrationController extends AbstractDelayedArbitrationController {

	public OblivionArbitrationController(Game game, PlayerCompleteServer target, TurnGameController turn) {
		super(game, target, turn);
	}

	@Override
	public boolean isArbitrationEffective(Card card) {
		return card.getSuit() != Suit.HEART;
	}

	@Override
	protected void handleEffect() {
		this.game.registerEventHandler(new SkipDealTurnEventHandler(this.target));
		DelayedStackItem item = this.target.removeDelayed(DelayedType.OBLIVION);
		this.game.getDeck().discard(item.delayed);
	}
	
	@Override
	protected String getNeutralizationMessage() {
		return this.target + " will start arbitration for Oblivion, use Neutralization?";
	}

	@Override
	protected void beforeEnd() {
		// if Oblivion is not effective, discard it
		if (this.target.hasDelayedType(DelayedType.OBLIVION)) {
			DelayedStackItem item = this.target.removeDelayed(DelayedType.OBLIVION);
			this.game.getDeck().discard(item.delayed);
		}
	}

}
