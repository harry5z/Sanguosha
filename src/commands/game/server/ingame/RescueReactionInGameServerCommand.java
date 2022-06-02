package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import cards.basics.Peach;
import cards.basics.Wine;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.DeathResolutionGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class RescueReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private Card card;
	
	/**
	 * Can use Wine or Peach to rescue oneself, or use Peach to rescue others. 
	 * If card is null, it means inaction
	 * 
	 * @param card : card used for rescue, or null if inaction
	 */
	public RescueReactionInGameServerCommand(Card card) {
		this.card = card;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				if (card != null) {
					DeathResolutionGameController controller = game.<DeathResolutionGameController>getNextGameController();
					controller.onRescueReaction(game, true);
					game.log(BattleLog
						.playerADidX(source, "contributed to the rescue")
						.onPlayer(controller.getDyingPlayer())
						.withCard(card)
					);
					game.pushGameController(new UseCardOnHandGameController(source, Set.of(card)));
				} else {
					game.<DeathResolutionGameController>getNextGameController().onRescueReaction(game, false);
				}				
			}
		};
	}
	
	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (card == null) {
			return;
		}
		
		try {
			card = game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Rescue Reaction: Card is invalid");
		}
		
		PlayerCompleteServer rescuer = game.<DeathResolutionGameController>getCurrentGameController().getCurrentRescuer();
		PlayerCompleteServer dyingPlayer = game.<DeathResolutionGameController>getCurrentGameController().getDyingPlayer();
		if (!rescuer.getCardsOnHand().contains(card)) {
			throw new IllegalPlayerActionException("Rescue Reaction: Player does not own the card used");
		}
		
		if (rescuer == dyingPlayer) {
			if (!(card instanceof Wine || card instanceof Peach)) {
				throw new IllegalPlayerActionException("Rescue Reaction: Must use Wine or Peach to save oneself");
			}
		} else {
			if (!(card instanceof Peach)) {
				throw new IllegalPlayerActionException("Rescue Reaction: Must use Peach to save another player");
			}
		}
	}

}
