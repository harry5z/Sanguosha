package commands.game.server.ingame;

import java.util.List;
import java.util.Set;

import cards.Card;
import cards.basics.Attack;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class AttackReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private Card attack;
	
	/**
	 * A regular Attack reaction to respond to Barbarian Invasion, Duel, Borrow Sword, etc.
	 * The card used must be on a player's hand, and must be an Attack card. If card is null, 
	 * The player gives up reaction
	 * 
	 * @param card : An Attack card from player's hand, or null which indicates inaction
	 */
	public AttackReactionInGameServerCommand(Card card) {
		this.attack = card;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				if (attack != null) {
					game.<AttackUsableGameController>getNextGameController().onAttackUsed(game, attack);
					game.pushGameController(new UseCardOnHandGameController(source, Set.of(attack)));
					game.log(BattleLog.playerADidXToCards(source, "used", List.of(attack)));
				} else {
					game.<AttackUsableGameController>getNextGameController().onAttackNotUsed(game);
				}				
			}
		};

	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (attack == null) {
			return;
		}
		try {
			attack = game.getDeck().getValidatedCard(attack);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Attack Reaction: Card is invalid");
		}

		if (!(this.attack instanceof Attack)) {
			throw new IllegalPlayerActionException("Attack Reaction: Card is not an Attack");
		}
		if (!source.getCardsOnHand().contains(attack)) {
			throw new IllegalPlayerActionException("Attack Reaction: Player does not own the card used");
		}
	}

}
