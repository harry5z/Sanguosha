package commands.server.ingame;

import java.util.Set;

import cards.Card;
import cards.basics.Peach;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.HealGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class UsePeachInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 9088626402130251064L;
	
	private Card card;

	public UsePeachInGameServerCommand(Card card) {
		this.card = card;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.pushGameController(new HealGameController(source, source));
				game.pushGameController(new UseCardOnHandGameController(source, Set.of(card)));
				game.log(BattleLog.playerADidXToCards(source, "used", Set.of(card)));
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (card == null) {
			throw new IllegalPlayerActionException("Peach: Card cannot be null");
		}
		
		try {
			card = (Peach) game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Peach: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Peach: Card is not a Peach");
		}

		if (!source.getCardsOnHand().contains(card)) {
			throw new IllegalPlayerActionException("Peach: Player does not own the card used");
		}
		
		if (!source.isDamaged()) {
			throw new IllegalPlayerActionException("Peach: Player is at full health");
		}		
	}

}
