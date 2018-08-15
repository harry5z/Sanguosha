package core.server.game.controllers.specials.instants;

import cards.Card;
import core.event.game.basic.RequestShowCardEvent;
import core.event.game.basic.RequestUseCardEvent;
import core.event.game.basic.RequestUseCardEvent.RequestUseCardPredicate;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import core.server.game.Damage;
import core.server.game.Game;
import core.server.game.Damage.Element;
import core.server.game.controllers.DamageGameController;
import core.server.game.controllers.interfaces.CardSelectableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class FireAttackController extends SingleTargetInstantSpecialGameController implements CardSelectableGameController {
	
	private Card shownCard;

	public FireAttackController(PlayerInfo source, PlayerInfo target, Game game) {
		super(source, target, game);
		this.shownCard = null;
	}

	@Override
	protected void takeEffect() {
		if (this.shownCard == null) {
			// ineffective if target has no card left on hand
			if (this.target.getHandCount() == 0) {
				this.stage = this.stage.nextStage();
				this.proceed();
				return;
			}
			try {
				this.game.emit(new RequestShowCardEvent(this.target.getPlayerInfo()));
			} catch (GameFlowInterruptedException e) {
				e.resume();
			}
		} else {
			// ineffective if source has no card left on hand
			if (this.source.getHandCount() == 0) {
				this.stage = this.stage.nextStage();
				this.proceed();
				return;
			}
			try {
				this.game.emit(
					new RequestUseCardEvent(this.source.getPlayerInfo())
						.addPredicate(RequestUseCardPredicate.sameSuit(this.shownCard.getSuit()))
				);
			} catch (GameFlowInterruptedException e) {
				e.resume();
			}
		}
	}

	@Override
	public void onCardSelected(Card card, PlayerCardZone zone) {
		if (this.shownCard == null) {
			this.shownCard = card;
			this.target.showCard(card);
			this.takeEffect();
		} else {
			// check if Fire Attack is effective
			this.stage = this.stage.nextStage();
			if (card != null) {
				try {
					// TODO: convert to controller
					this.source.discardCard(card);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				this.game.pushGameController(new DamageGameController(new Damage(1, Element.FIRE, this.source, this.target), this.game));
			}
			this.game.getGameController().proceed();
		}
	}

}
