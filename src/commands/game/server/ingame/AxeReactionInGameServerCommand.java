package commands.game.server.ingame;

import java.util.Map;
import java.util.Map.Entry;

import cards.Card;
import core.player.PlayerCardZone;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.equipment.AxeGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class AxeReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final Map<Card, PlayerCardZone> cards;
	
	public AxeReactionInGameServerCommand(Map<Card, PlayerCardZone> cards) {
		this.cards = cards;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				AxeGameController controller = game.<AxeGameController>getNextGameController();
				for (Entry<Card, PlayerCardZone> entry : cards.entrySet()) {
					controller.onCardSelected(game, entry.getKey(), entry.getValue());
				}
				controller.onDecisionMade(cards.size() == 2);				
			}
		};

	}

}
