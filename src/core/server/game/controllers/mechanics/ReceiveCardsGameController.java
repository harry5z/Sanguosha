package core.server.game.controllers.mechanics;

import java.util.Collection;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;

public class ReceiveCardsGameController extends AbstractGameController<ReceiveCardsGameController.ReceiveCardStage> {

	public static enum ReceiveCardStage implements GameControllerStage<ReceiveCardStage> {
		ADD_CARDS,
		END,
	}
	
	private final PlayerCompleteServer target;
	private final Collection<Card> cards;
	private final boolean isPublic; // whether the received cards are publicly known
	
	public ReceiveCardsGameController(
		PlayerCompleteServer target,
		Collection<Card> cards,
		boolean isPublic
	) {
		this.target = target;
		this.cards = cards;
		this.isPublic = isPublic;
	}

	@Override
	protected void handleStage(GameInternal game, ReceiveCardStage stage) throws GameFlowInterruptedException {
		switch(stage) {
			case ADD_CARDS:
				this.nextStage();
				this.target.addCards(this.cards);
				if (isPublic) {
					game.log(BattleLog.playerADidXToCards(target, "received", cards));
				} else {
					game.log(BattleLog.playerADidX(target, "received <b>" + cards.size() + "</b> cards"));
				}
				break;
			case END:
				break;
		}

	}

	@Override
	protected ReceiveCardStage getInitialStage() {
		return ReceiveCardStage.ADD_CARDS;
	}

}
