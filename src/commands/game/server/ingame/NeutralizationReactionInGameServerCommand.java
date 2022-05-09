package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.SpecialGameController;

public class NeutralizationReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 8912576615284742483L;
	
	private final PlayerInfo source;
	private final Card neutralization;
	
	public NeutralizationReactionInGameServerCommand(PlayerInfo source, Card neutralization) {
		this.source = source;
		this.neutralization = neutralization;
	}

	@Override
	public void execute(Game game) {
		if (neutralization != null) {
			game.<SpecialGameController>getGameController().onNeutralized();
			game.pushGameController(new UseCardOnHandGameController(game, game.findPlayer(source), Set.of(neutralization)));
		} else {
			game.<SpecialGameController>getGameController().onNeutralizationCanceled();
		}
		game.getGameController().proceed();
	}

}
