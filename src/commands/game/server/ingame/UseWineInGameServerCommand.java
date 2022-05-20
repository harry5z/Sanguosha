package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import cards.basics.Wine;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;
import exceptions.server.game.InvalidPlayerCommandException;

public class UseWineInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 5470029734787854360L;
	
	private Card wine;

	public UseWineInGameServerCommand(Card card) {
		this.wine = card;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				try {
					game.getCurrentPlayer().useWine();
					game.pushGameController(new UseCardOnHandGameController(game.getCurrentPlayer(), Set.of(wine)));
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (wine == null) {
			throw new IllegalPlayerActionException("Wine: Card cannot be null");
		}
		
		try {
			wine = (Wine) game.getDeck().getValidatedCard(wine);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Wine: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Wine: Card is not a Wine");
		}

		if (!source.getCardsOnHand().contains(wine)) {
			throw new IllegalPlayerActionException("Wine: Player does not own the card used");
		}
		
		if (source.isWineUsed()) {
			throw new IllegalPlayerActionException("Wine: Wine is already used this turn");
		}
	}

}
