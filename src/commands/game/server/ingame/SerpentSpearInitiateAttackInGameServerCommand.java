package commands.game.server.ingame;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import cards.Card.Color;
import cards.basics.Attack;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.AttackGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;
import exceptions.server.game.InvalidPlayerCommandException;

public class SerpentSpearInitiateAttackInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private final Set<PlayerInfo> targets;
	private Set<Card> cards;
	
	public SerpentSpearInitiateAttackInGameServerCommand(Set<PlayerInfo> targets, Set<Card> cards) {
		this.targets = targets;
		this.cards = cards;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				try {
					Set<PlayerCompleteServer> set = targets.stream().map(target -> game.findPlayer(target)).collect(Collectors.toSet());
					source.useAttack();
					Color color = cards.stream().map(card -> card.getColor()).reduce(
						cards.iterator().next().getColor(),
						(c1, c2) -> c1 == c2 ? c1 : Color.COLORLESS
					);
					game.pushGameController(new AttackGameController(source, set, new Attack(color)));
					game.pushGameController(new UseCardOnHandGameController(source, cards));
				} catch (InvalidPlayerCommandException e) {
					// TODO handle error
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (source.getAttackUsed() >= source.getAttackLimit()) {
			throw new IllegalPlayerActionException("Serpent Spear: Player may no longer attack this turn");
		}
		
		if (cards == null || cards.size() != 2) {
			throw new IllegalPlayerActionException("Serpent Spear: Must use 2 cards");
		}
		
		if (targets == null) {
			throw new IllegalPlayerActionException("Serpent Spear: Targets cannot be null");
		}
		
		if (targets.size() > source.getAttackTargetLimit()) {
			throw new IllegalPlayerActionException("Serpent Spear: Too many targets");
		}
		
		Set<Card> replacement = new HashSet<>();
		for (Card card : cards) {
			try {
				card = game.getDeck().getValidatedCard(card);
				replacement.add(card);
			} catch (InvalidCardException e) {
				throw new IllegalPlayerActionException("Serpent Spear: Card is invalid. " + e.getMessage());
			}
			if (!source.getCardsOnHand().contains(card)) {
				throw new IllegalPlayerActionException("Serpent Spear: Player does not own the card used");
			}
		}
		cards = replacement;	
		
		Set<PlayerCompleteServer> tgts = new HashSet<>();
		for (PlayerInfo target : targets) {
			PlayerCompleteServer other = game.findPlayer(target);
			tgts.add(other);
			if (other == null) {
				throw new IllegalPlayerActionException("Serpent Spear: Target not found");
			}
			if (source.equals(other)) {
				throw new IllegalPlayerActionException("Serpent Spear: Target cannot be oneself");
			}
			if (!source.isPlayerInAttackRange(other, game.getNumberOfPlayersAlive())) {
				throw new IllegalPlayerActionException("Serpent Spear: Target not in attack range");
			}
			if (!other.isAlive()) {
				throw new IllegalPlayerActionException("Serpent Spear: Target not alive");
			}
		}
		if (tgts.size() < targets.size()) {
			throw new IllegalPlayerActionException("Serpent Spear: Cannot have duplicated targets");
		}

	}

}
