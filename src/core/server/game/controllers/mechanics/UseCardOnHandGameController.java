package core.server.game.controllers.mechanics;

import java.util.Collection;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;
import exceptions.server.game.InvalidPlayerCommandException;

public class UseCardOnHandGameController extends AbstractGameController<UseCardOnHandGameController.UseCardOnHandStage> {
	
	public static enum UseCardOnHandStage implements GameControllerStage<UseCardOnHandStage> {
		USE_CARDS,
		END,
	}
	
	private final PlayerCompleteServer player;
	private final Collection<Card> cards;

	public UseCardOnHandGameController(Game game, PlayerCompleteServer player, Collection<Card> cards) {
		super(game);
		this.player = player;
		this.cards = cards;
	}

	@Override
	public void proceed() {
		switch (this.stage) {
			case USE_CARDS:
				try {
					this.player.useCards(this.cards);
					this.stage = this.stage.nextStage();
					this.proceed();
				} catch (InvalidPlayerCommandException e) {
					// TODO handle error
					e.printStackTrace();
				}
				break;
			case END:
				this.onUnloaded();
				this.game.getGameController().proceed();
				break;
		}
	}

	@Override
	protected UseCardOnHandStage getInitialStage() {
		return UseCardOnHandStage.USE_CARDS;
	}

}
