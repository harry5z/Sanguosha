package commands.game.server.ingame;

import java.util.Map;
import java.util.Map.Entry;

import cards.Card;
import core.player.PlayerCardZone;
import core.server.game.Game;
import core.server.game.controllers.equipment.AxeGameController;

public class AxeReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final Map<Card, PlayerCardZone> cards;
	
	public AxeReactionInGameServerCommand(Map<Card, PlayerCardZone> cards) {
		this.cards = cards;
	}

	@Override
	public void execute(Game game) {
		AxeGameController controller = game.getGameController();
		for (Entry<Card, PlayerCardZone> entry : this.cards.entrySet()) {
			controller.onCardSelected(entry.getKey(), entry.getValue());
		}
		controller.onDecisionMade(this.cards.size() == 2);
		game.getGameController().proceed();
	}

}
