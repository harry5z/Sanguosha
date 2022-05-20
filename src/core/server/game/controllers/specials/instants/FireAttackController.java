package core.server.game.controllers.specials.instants;

import cards.Card;
import commands.game.client.RequestShowCardGameUIClientCommand;
import commands.game.client.RequestUseCardGameUIClientCommand;
import commands.game.client.RequestUseCardGameUIClientCommand.RequestUseCardFilter;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.Damage.Element;
import core.server.game.GameInternal;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.DamageGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidPlayerCommandException;

public class FireAttackController extends SingleTargetInstantSpecialGameController implements CardSelectableGameController {
	
	private Card shownCard;

	public FireAttackController(PlayerCompleteServer source, PlayerCompleteServer target) {
		super(source, target);
		this.shownCard = null;
	}

	@Override
	protected void takeEffect(GameInternal game) throws GameFlowInterruptedException {
		if (this.shownCard == null) {
			if (this.target.getHandCount() == 0) {
				// ineffective if target has no card left on hand
				this.nextStage();
				return;
			}
			throw new GameFlowInterruptedException(
				new RequestShowCardGameUIClientCommand(
					this.target,
					this.source + " used Fire Attack on you, please show a card."
				)
			);
		} else {
			// ineffective if source has no card left on hand
			if (this.source.getHandCount() == 0) {
				this.nextStage();
				return;
			}
			throw new GameFlowInterruptedException(
				new RequestUseCardGameUIClientCommand(
					source.getPlayerInfo(),
					"Discard a card of suit " + shownCard.getSuit().toString() + " to finish Fire Attack?",
					RequestUseCardFilter.sameSuit(shownCard.getSuit())
				)
			);
		}
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.source + " used Fire Attack on " + this.target + ", use Nullification?";
	}

	@Override
	public void onCardSelected(GameInternal game, Card card, PlayerCardZone zone) {
		if (this.shownCard == null) {
			this.shownCard = card;
			this.target.showCard(card);
		} else {
			// check if Fire Attack is effective
			this.nextStage();
			try {
				// TODO: convert to controller
				this.source.discardCard(card);
			} catch (InvalidPlayerCommandException e) {
				e.printStackTrace();
			}
			game.pushGameController(new DamageGameController(new Damage(1, Element.FIRE, this.source, this.target)));
		}
	}

	@Override
	public void validateCardSelected(GameInternal game, Card card, PlayerCardZone zone) throws IllegalPlayerActionException {
		if (card == null) {
			throw new IllegalPlayerActionException("Fire Attack: Card cannot be null");
		}
		if (this.shownCard == null) {
			if (!target.getCardsOnHand().contains(card)) {
				throw new IllegalPlayerActionException("Fire Attack: Target does not have this card on hand");
			}
		} else {
			if (!source.getCardsOnHand().contains(card)) {
				throw new IllegalPlayerActionException("Fire Attack: Source does not have this card on hand");
			}
			if (card.getSuit() != shownCard.getSuit()) {
				throw new IllegalPlayerActionException("Fire Attack: Shown card and discarded card are not of the same suit");
			}
		}
	}

}
