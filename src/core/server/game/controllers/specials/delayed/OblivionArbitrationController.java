package core.server.game.controllers.specials.delayed;

import cards.Card;
import cards.Card.Suit;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.mechanics.TurnGameController.TurnStage;
import utils.DelayedStackItem;
import utils.DelayedType;

public class OblivionArbitrationController extends AbstractDelayedArbitrationController {

	public OblivionArbitrationController(PlayerCompleteServer target) {
		super(target);
	}

	@Override
	public boolean isArbitrationEffective(Card card) {
		return card.getSuit() != Suit.HEART;
	}

	@Override
	protected void handleEffect(GameInternal game) {
		game.getTurnController().skipStage(TurnStage.DEAL);
		DelayedStackItem item = this.target.removeDelayed(DelayedType.OBLIVION);
		game.getDeck().discard(item.delayed);
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.target + " will start arbitration for Oblivion, use Nullification?";
	}

	@Override
	protected void beforeEnd(GameInternal game) {
		// if Oblivion is not effective, discard it
		if (this.target.hasDelayedType(DelayedType.OBLIVION)) {
			DelayedStackItem item = this.target.removeDelayed(DelayedType.OBLIVION);
			game.getDeck().discard(item.delayed);
		}
	}

	@Override
	protected BattleLog getLogOnEffectiveArbitration() {
		return BattleLog.custom("<b>Oblivion</b> is effective");
	}

	@Override
	protected BattleLog getLogOnIneffectiveArbitration() {
		return BattleLog.custom("<b>Oblivion</b> is not effective");
	}
}
