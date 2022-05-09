package core.server.game.controllers.mechanics;

import java.util.Collection;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.EnumWithNextStage;

public class UseCardOnHandGameController extends AbstractGameController {
	
	public static enum UseCardOnHandStage implements EnumWithNextStage<UseCardOnHandStage> {
		USE_CARDS,
		END,
	}
	
	private UseCardOnHandStage stage;
	private final PlayerCompleteServer player;
	private final Collection<Card> cards;

	public UseCardOnHandGameController(Game game, PlayerCompleteServer player, Collection<Card> cards) {
		super(game);
		this.player = player;
		this.cards = cards;
		this.stage = UseCardOnHandStage.USE_CARDS;
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

}
