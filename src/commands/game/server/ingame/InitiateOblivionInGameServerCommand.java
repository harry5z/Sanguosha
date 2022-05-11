package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedType;

public class InitiateOblivionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private final Card card;

	public InitiateOblivionInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
		this.card = card;
	}

	@Override
	protected GameController getGameController(Game game) {
		return new AbstractSingleStageGameController(game) {
			
			@Override
			protected void handleOnce() throws GameFlowInterruptedException {
				PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
				try {
					currentPlayer.removeCardFromHand(card);
					game.findPlayer(target).pushDelayed(card, DelayedType.OBLIVION);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
}
