package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import cards.Card.Color;
import cards.equipments.Equipment;
import core.heroes.skills.GanNingSuddenStrikeHeroSkill;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.instants.SabotageGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class SuddenStrikeSkillInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private Card card;
	private final PlayerCardZone zone;
	private final PlayerInfo target;
	
	public SuddenStrikeSkillInGameServerCommand(Card card, PlayerCardZone zone, PlayerInfo target) {
		this.card = card;
		this.zone = zone;
		this.target = target;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.pushGameController(new SabotageGameController(game.getCurrentPlayer(), game.findPlayer(target)));
				if (zone == PlayerCardZone.HAND) {
					game.pushGameController(new UseCardOnHandGameController(game.getCurrentPlayer(), Set.of(card)));
				} else if (zone == PlayerCardZone.EQUIPMENT) {
					game.pushGameController(new RecycleCardsGameController(game.getCurrentPlayer(), Set.of(card)));
					game.pushGameController(new UnequipGameController(game.getCurrentPlayer(), ((Equipment) card).getEquipmentType()));
				}
				game.log(BattleLog
					.playerAUsedSkill(source, new GanNingSuddenStrikeHeroSkill())
					.withCard(card)
					.to("convert into Sabotage")
					.onPlayer(game.findPlayer(target))
				);
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (card == null) {
			throw new IllegalPlayerActionException("Sudden Strike: Card cannot be null");
		}
		
		try {
			card = game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Sudden Strike: Card is invalid");
		}
		
		if (card.getColor() != Color.BLACK) {
			throw new IllegalPlayerActionException("Sudden Strike: Card must be BLACK");
		}

		switch (zone) {
			case HAND:
				if (!source.getCardsOnHand().contains(card)) {
					throw new IllegalPlayerActionException("Sudden Strike: Player does not own the card used");
				}
				break;
			case EQUIPMENT:
				if (!(card instanceof Equipment)) {
					throw new IllegalPlayerActionException("Sudden Strike: Card is not an Equipment");
				}
				if (!source.isEquippedWith((Equipment) card)) {
					throw new IllegalPlayerActionException("Sudden Strike: Player is not equipped with " + card);
				}
				break;
			default:
				throw new IllegalPlayerActionException("Sudden Strike: Invalid zone");
		}
		
		PlayerCompleteServer other = game.findPlayer(target);
		if (other == null) {
			throw new IllegalPlayerActionException("Sudden Strike: Target not found");
		}
		if (source.equals(other)) {
			throw new IllegalPlayerActionException("Sudden Strike: Target cannot be oneself");
		}
		if (!other.isAlive()) {
			throw new IllegalPlayerActionException("Sudden Strike: Target not alive");
		}
		if (!(other.isEquipped() || other.getHandCount() > 0 || other.getDelayedQueue().size() > 0)) {
			throw new IllegalPlayerActionException("Sudden Strike: Target has no card to discard");
		}		
	}

}
