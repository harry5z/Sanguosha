package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractInitiationInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private final PlayerInfo target;
	private final Card card;

	public AbstractInitiationInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
		this.card = card;
	}

	@Override
	public final GameController getGameController() {
		return new AbstractSingleStageGameController() {
			@Override
			protected void handleOnce(Game game) throws GameFlowInterruptedException {
				game.pushGameController(getInitiationGameController(game, target != null ? game.findPlayer(target) : null));
				if (card != null) {
					PlayerCompleteServer source = game.getCurrentPlayer();
					game.pushGameController(new UseCardOnHandGameController(source, Set.of(card)));			
				}				
			}
		};
	}
	
	protected abstract GameController getInitiationGameController(Game game, PlayerCompleteServer target);

}
