package core.server.game.controllers.specials.instants;

import cards.Card;
import core.event.game.basic.RequestShowCardEvent;
import core.event.game.basic.RequestUseCardEvent;
import core.event.game.basic.RequestUseCardEvent.RequestUseCardPredicate;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.Damage.Element;
import core.server.game.Game;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.DamageGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class FireAttackController extends SingleTargetInstantSpecialGameController implements CardSelectableGameController {
	
	private Card shownCard;

	public FireAttackController(PlayerCompleteServer source, PlayerCompleteServer target) {
		super(source, target);
		this.shownCard = null;
	}

	@Override
	protected void takeEffect(Game game) throws GameFlowInterruptedException {
		if (this.shownCard == null) {
			if (this.target.getHandCount() == 0) {
				// ineffective if target has no card left on hand
				this.nextStage();
				return;
			}
			game.emit(new RequestShowCardEvent(
				this.target.getPlayerInfo(),
				this.source + " used Fire Attack on you, please show a card."
			));
			throw new GameFlowInterruptedException();
		} else {
			// ineffective if source has no card left on hand
			if (this.source.getHandCount() == 0) {
				this.nextStage();
				return;
			}
			game.emit(
				new RequestUseCardEvent(
					this.source.getPlayerInfo(),
					"Discard a card of suit " + this.shownCard.getSuit().toString() + " to finish Fire Attack?"
				).addPredicate(RequestUseCardPredicate.sameSuit(this.shownCard.getSuit()))
			);
			throw new GameFlowInterruptedException();
		}
	}
	
	@Override
	protected String getNeutralizationMessage() {
		return this.source + " used Fire Attack on " + this.target + ", use Neutralization?";
	}

	@Override
	public void onCardSelected(Game game, Card card, PlayerCardZone zone) {
		if (this.shownCard == null) {
			this.shownCard = card;
			this.target.showCard(card);
		} else {
			// check if Fire Attack is effective
			this.nextStage();
			if (card != null) {
				try {
					// TODO: convert to controller
					this.source.discardCard(card);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				game.pushGameController(new DamageGameController(new Damage(1, Element.FIRE, this.source, this.target)));
			}
		}
	}

}
