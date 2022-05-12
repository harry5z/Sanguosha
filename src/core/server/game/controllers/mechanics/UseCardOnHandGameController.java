package core.server.game.controllers.mechanics;

import java.util.Collection;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class UseCardOnHandGameController extends AbstractGameController<UseCardOnHandGameController.UseCardOnHandStage> {
	
	public static enum UseCardOnHandStage implements GameControllerStage<UseCardOnHandStage> {
		USE_CARDS,
		END,
	}
	
	private final PlayerCompleteServer player;
	private final Collection<Card> cards;

	public UseCardOnHandGameController(PlayerCompleteServer player, Collection<Card> cards) {
		this.player = player;
		this.cards = cards;
	}

	@Override
	protected void handleStage(GameInternal game, UseCardOnHandStage stage) throws GameFlowInterruptedException {
		switch (stage) {
			case USE_CARDS:
				try {
					this.nextStage();
					this.player.useCards(this.cards);
				} catch (InvalidPlayerCommandException e) {
					// TODO handle error
					e.printStackTrace();
				}
				break;
			case END:
				break;
		}
	}

	@Override
	protected UseCardOnHandStage getInitialStage() {
		return UseCardOnHandStage.USE_CARDS;
	}

}
