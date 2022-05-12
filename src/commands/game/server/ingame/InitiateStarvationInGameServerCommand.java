package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedType;

public class InitiateStarvationInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private final Card card;

	public InitiateStarvationInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
		this.card = card;
	}

	@Override
	protected GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
				try {
					currentPlayer.removeCardFromHand(card);
					game.findPlayer(target).pushDelayed(card, DelayedType.STARVATION);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
