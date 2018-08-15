package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.FireAttackController;

public class InitiateFireAttackInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;

	public InitiateFireAttackInGameServerCommand(PlayerInfo target, Card card) {
		super(target, card);
	}

	@Override
	protected GameController getController(Game game, PlayerInfo target) {
		return new FireAttackController(game.getCurrentPlayer().getPlayerInfo(), target, game);
	}

}
