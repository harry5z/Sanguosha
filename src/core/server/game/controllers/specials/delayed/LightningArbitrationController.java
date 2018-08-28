package core.server.game.controllers.specials.delayed;

import cards.Card;
import cards.Card.Suit;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.Damage.Element;
import core.server.game.Game;
import core.server.game.controllers.DamageGameController;
import core.server.game.controllers.TurnGameController;
import utils.DelayedStackItem;
import utils.DelayedType;

public class LightningArbitrationController extends AbstractDelayedArbitrationController {
	
	private boolean effective;

	public LightningArbitrationController(Game game, PlayerCompleteServer target, TurnGameController turn) {
		super(game, target, turn);
		this.effective = false;
	}

	@Override
	public void onArbitrationCompleted(Card card) {
		int num = card.getNumber();
		if (card.getSuit() == Suit.SPADE && num >= 2 && num <= 9) {
			this.effective = true;
		} else {
			this.effective = false;
		}
	}

	@Override
	protected void takeEffect() {
		if (this.effective) {
			this.game.pushGameController(new DamageGameController(new Damage(3, Element.THUNDER, null, this.target), this.game));
		}
	}

	@Override
	protected void beforeEnd() {
		DelayedStackItem item = this.target.removeDelayed(DelayedType.LIGHTNING);
		if (this.effective) {
			this.game.getDeck().discard(item.delayed);
		} else {
			PlayerCompleteServer next = this.game.getNextPlayerAlive(this.target);
			// WARNING: Potential infinite loop if more than 2 Lightnings exist
			while (next.hasDelayedType(DelayedType.LIGHTNING)) {
				next = this.game.getNextPlayerAlive(next);
			}
			next.pushDelayed(item.delayed, item.type);
		}
	}

}
