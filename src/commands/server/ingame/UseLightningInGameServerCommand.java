package commands.server.ingame;

import java.util.List;

import cards.Card;
import cards.specials.delayed.Lightning;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedType;

public class UseLightningInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 9088626402130251064L;
	
	private Card card;

	public UseLightningInGameServerCommand(Card card) {
		this.card = card;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
				try {
					currentPlayer.removeCardFromHand(card);
					currentPlayer.pushDelayed(card, DelayedType.LIGHTNING);
					game.log(BattleLog
						.playerADidXToCards(currentPlayer, "used", List.of(card))
						.onPlayer(currentPlayer)
					);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (card == null) {
			throw new IllegalPlayerActionException("Lightning: Card cannot be null");
		}
		
		try {
			card = (Lightning) game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Lightning: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Lightning: Card is not a Lightning");
		}

		if (!source.getCardsOnHand().contains(card)) {
			throw new IllegalPlayerActionException("Lightning: Player does not own the card used");
		}
		
		if (source.hasDelayedType(DelayedType.LIGHTNING)) {
			throw new IllegalPlayerActionException("Lightning: Player already has Lightning");
		}
	}
	
}
