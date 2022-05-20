package commands.game.server.ingame;

import cards.Card;
import cards.specials.delayed.Starvation;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedType;

public class InitiateStarvationInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private Card card;

	public InitiateStarvationInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
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
					game.findPlayer(target).pushDelayed(card, DelayedType.STARVATION);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (card == null) {
			throw new IllegalPlayerActionException("Starvation: Card cannot be null");
		}
		
		try {
			card = (Starvation) game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Starvation: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Starvation: Card is not a Starvation");
		}

		if (!source.getCardsOnHand().contains(card)) {
			throw new IllegalPlayerActionException("Starvation: Player does not own the card used");
		}
		
		PlayerCompleteServer other = game.findPlayer(target);
		if (other == null) {
			throw new IllegalPlayerActionException("Starvation: Target not found");
		}
		if (source.equals(other)) {
			throw new IllegalPlayerActionException("Starvation: Target cannot be oneself");
		}
		if (!source.isPlayerInDistance(other, game.getNumberOfPlayersAlive())) {
			throw new IllegalPlayerActionException("Starvation: Target is not in distance");
		}
		if (other.hasDelayedType(DelayedType.STARVATION)) {
			throw new IllegalPlayerActionException("Starvation: Target already has Starvation");
		}
		if (!other.isAlive()) {
			throw new IllegalPlayerActionException("Starvation: Target not alive");
		}		
	}
}
