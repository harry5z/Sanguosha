package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.BorrowSwordGameController;

public class InitiateBorrowSwordInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	private final PlayerInfo attackTarget;

	public InitiateBorrowSwordInGameServerCommand(PlayerInfo target, PlayerInfo attackTarget, Card card) {
		super(target, card);
		this.attackTarget = attackTarget;
	}

	@Override
	protected GameController getInitiationGameController(Game game, PlayerCompleteServer target) {
		return new BorrowSwordGameController(game.getCurrentPlayer(), target, game.findPlayer(attackTarget));
	}

}
