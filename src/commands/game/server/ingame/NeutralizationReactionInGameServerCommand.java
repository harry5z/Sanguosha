package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.specials.SpecialGameController;
import exceptions.server.game.InvalidPlayerCommandException;

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
			try {
				// TODO: move usage of card into a separate command since things may happen in between
				game.findPlayer(source).useCard(neutralization);
				game.<SpecialGameController>getGameController().onNeutralized();
			} catch (InvalidPlayerCommandException e) {
				e.printStackTrace();
			}
		} else {
			game.<SpecialGameController>getGameController().onNeutralizationCanceled();
		}
	}

}
