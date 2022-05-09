package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;

public abstract class AbstractInitiationInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private final PlayerInfo target;
	private final Card card;

	public AbstractInitiationInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
		this.card = card;
	}

	@Override
	public final void execute(Game game) {
		game.pushGameController(this.getController(game, this.target));
		if (card != null) {
			// TODO specify source in command since initiator may not be the current player
			PlayerCompleteServer source = game.getCurrentPlayer();
			game.pushGameController(new UseCardOnHandGameController(game, source, Set.of(card)));			
		}
		game.getGameController().proceed();
	}
	
	protected abstract GameController getController(Game game, PlayerInfo target);

}
