package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.SpecialGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class NullificationReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 8912576615284742483L;
	
	private final PlayerInfo source;
	private final Card nullification;
	
	public NullificationReactionInGameServerCommand(PlayerInfo source, Card nullification) {
		this.source = source;
		this.nullification = nullification;
	}

	@Override
	protected GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(Game game) throws GameFlowInterruptedException {
				if (nullification != null) {
					game.<SpecialGameController>getNextGameController().onNullified();
					game.pushGameController(new UseCardOnHandGameController(game.findPlayer(source), Set.of(nullification)));
				} else {
					game.<SpecialGameController>getNextGameController().onNullificationCanceled();
				}
			}
		};
	}

}
