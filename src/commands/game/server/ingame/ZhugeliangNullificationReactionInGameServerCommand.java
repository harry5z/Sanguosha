package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import cards.Card.Color;
import core.heroes.skills.ZhugeliangSeeThroughHeroSkill;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.SpecialGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class ZhugeliangNullificationReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private Card nullification;
	
	public ZhugeliangNullificationReactionInGameServerCommand(Card nullification) {
		this.nullification = nullification;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.<SpecialGameController>getNextGameController().onNullified();
				game.pushGameController(new UseCardOnHandGameController(source, Set.of(nullification)));
				game.log(BattleLog
					.playerAUsedSkill(source, new ZhugeliangSeeThroughHeroSkill())
					.withCard(nullification)
					.to("convert into Nullification")
				);
			}
		};
	}
	
	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (nullification == null) {
			throw new IllegalPlayerActionException("See Through: Card cannot be null");
		}
		
		try {
			nullification = game.getDeck().getValidatedCard(nullification);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("See Through: Card is invalid");
		}
		
		if (nullification.getColor() != Color.BLACK) {
			throw new IllegalPlayerActionException("See Through: Card must be BLACK");
		}

		if (!source.getCardsOnHand().contains(nullification)) {
			throw new IllegalPlayerActionException("See Through: Player does not own the card used");
		}
	}

}
