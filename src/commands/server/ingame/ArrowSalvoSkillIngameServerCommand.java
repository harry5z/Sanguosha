package commands.server.ingame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cards.Card;
import core.heroes.skills.YuanShaoArrowSalvoHeroSkill;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.ArrowSalvoGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;
import exceptions.server.game.InvalidPlayerCommandException;

public class ArrowSalvoSkillIngameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private List<Card> cards;
	
	public ArrowSalvoSkillIngameServerCommand(List<Card> cards) {
		this.cards = cards;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				try {
					// TODO: convert to discard controller
					source.discardCards(cards);
					Queue<PlayerCompleteServer> targets = new LinkedList<>();
					PlayerCompleteServer next = game.getNextPlayerAlive(source);
					while (next != source) {
						targets.add(next);
						next = game.getNextPlayerAlive(next);
					}
					game.pushGameController(new ArrowSalvoGameController(source, targets));
					game.log(BattleLog
						.playerAUsedSkill(source, new YuanShaoArrowSalvoHeroSkill())
						.withCards(cards)
						.to("convert into Arrow Salvo")
					);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (cards == null || cards.isEmpty()) {
			throw new IllegalPlayerActionException("Arrow Salvo Skill: cards cannot be null or empty");
		}
		
		if (cards.size() != 2) {
			throw new IllegalPlayerActionException("Arrow Salvo Skill: must use two cards to initiate");
		}
		
		List<Card> newCards = new ArrayList<>();
		try {
			Card card1 = game.getDeck().getValidatedCard(cards.get(0));
			Card card2 = game.getDeck().getValidatedCard(cards.get(1));
			
			if (card1 == card2) {
				throw new IllegalPlayerActionException("Arrow Salvo Skill: Two cards cannot be the same");
			}
			
			if (!source.getCardsOnHand().contains(card1) || !source.getCardsOnHand().contains(card2)) {
				throw new IllegalPlayerActionException("Arrow Salvo Skill: Player does not own the card used");
			}
			
			if (card1.getSuit() != card2.getSuit()) {
				throw new IllegalPlayerActionException("Arrow Salvo Skill: Two cards are not of the same suit");
			}
			newCards.add(card1);
			newCards.add(card2);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Arrow Salvo Skill: Card is invalid. " + e.getMessage());
		}
		cards = newCards;
	}

}
