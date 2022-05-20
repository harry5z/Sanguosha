package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import cards.specials.instant.Nullification;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.SpecialGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class NullificationReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 8912576615284742483L;
	
	private Card nullification;
	
	public NullificationReactionInGameServerCommand(Card nullification) {
		this.nullification = nullification;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				if (nullification != null) {
					game.<SpecialGameController>getNextGameController().onNullified();
					game.pushGameController(new UseCardOnHandGameController(source, Set.of(nullification)));
				} else {
					game.<SpecialGameController>getNextGameController().onNullificationCanceled();
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (nullification == null) {
			return;
		}
		
		try {
			nullification = (Nullification) game.getDeck().getValidatedCard(nullification);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Nullification: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Nullification: Card is not a Nullification");
		}

		if (!source.getCardsOnHand().contains(nullification)) {
			throw new IllegalPlayerActionException("Nullification: Player does not own the card used");
		}		
	}

}
