package commands.server.ingame;

import java.util.List;

import cards.Card;
import cards.specials.delayed.Oblivion;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedType;

public class InitiateOblivionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private Card card;

	public InitiateOblivionInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
		this.card = card;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				try {
					source.removeCardFromHand(card);
					game.findPlayer(target).pushDelayed(card, DelayedType.OBLIVION);
					game.log(BattleLog
						.playerADidXToCards(source, "used", List.of(card))
						.onPlayer(game.findPlayer(target))
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
			throw new IllegalPlayerActionException("Oblivion: Card cannot be null");
		}
		
		try {
			card = (Oblivion) game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Oblivion: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Oblivion: Card is not an Oblivion");
		}

		if (!source.getCardsOnHand().contains(card)) {
			throw new IllegalPlayerActionException("Oblivion: Player does not own the card used");
		}
		
		PlayerCompleteServer other = game.findPlayer(target);
		if (other == null) {
			throw new IllegalPlayerActionException("Oblivion: Target not found");
		}
		if (source.equals(other)) {
			throw new IllegalPlayerActionException("Oblivion: Target cannot be oneself");
		}
		if (other.hasDelayedType(DelayedType.OBLIVION)) {
			throw new IllegalPlayerActionException("Oblivion: Target already has Oblivion");
		}
		if (!other.isAlive()) {
			throw new IllegalPlayerActionException("Oblivion: Target not alive");
		}
	}
	
}
