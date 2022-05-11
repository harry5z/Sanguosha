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

public class NeutralizationReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 8912576615284742483L;
	
	private final PlayerInfo source;
	private final Card neutralization;
	
	public NeutralizationReactionInGameServerCommand(PlayerInfo source, Card neutralization) {
		this.source = source;
		this.neutralization = neutralization;
	}

	@Override
	protected GameController getGameController(Game game) {
		return new AbstractSingleStageGameController(game) {
			
			@Override
			protected void handleOnce() throws GameFlowInterruptedException {
				if (neutralization != null) {
					game.<SpecialGameController>getNextGameController().onNeutralized();
					game.pushGameController(new UseCardOnHandGameController(game, game.findPlayer(source), Set.of(neutralization)));
				} else {
					game.<SpecialGameController>getNextGameController().onNeutralizationCanceled();
				}
			}
		};
	}

}
