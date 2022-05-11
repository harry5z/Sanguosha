package core.server.game.controllers.specials.delayed;

import cards.Card;
import cards.Card.Suit;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.Damage.Element;
import core.server.game.Game;
import core.server.game.controllers.mechanics.DamageGameController;
import core.server.game.controllers.mechanics.TurnGameController;
import utils.DelayedStackItem;
import utils.DelayedType;

public class LightningArbitrationController extends AbstractDelayedArbitrationController {
	
	public LightningArbitrationController(Game game, PlayerCompleteServer target, TurnGameController turn) {
		super(game, target, turn);
	}

	@Override
	public boolean isArbitrationEffective(Card card) {
		return card.getSuit() == Suit.SPADE && card.getNumber() >= 2 && card.getNumber() <= 9;
	}

	@Override
	protected void handleEffect() {
		if (this.effective) {
			DelayedStackItem item = this.target.removeDelayed(DelayedType.LIGHTNING);
			this.game.getDeck().discard(item.delayed);
			this.game.pushGameController(new DamageGameController(new Damage(3, Element.THUNDER, null, this.target), this.game));
		}
	}
	
	@Override
	protected String getNeutralizationMessage() {
		return this.target + " will start arbitration for Lightning, use Neutralization?";
	}

	@Override
	protected void beforeEnd() {
		// if Lightning is not effective, it is transferred to the next player
		if (this.target.hasDelayedType(DelayedType.LIGHTNING)) {
			DelayedStackItem item = this.target.removeDelayed(DelayedType.LIGHTNING);
			PlayerCompleteServer next = this.game.getNextPlayerAlive(this.target);
			// NOTE: May cause an infinite loop if more than 2 Lightning cards exist
			while (next.hasDelayedType(DelayedType.LIGHTNING)) {
				next = this.game.getNextPlayerAlive(next);
			}
			next.pushDelayed(item.delayed, item.type);
		}
	}

}
