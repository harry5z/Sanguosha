package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateHarvestInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class HarvestOperation extends AbstractCardUsageOperation {

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new InitiateHarvestInGameServerCommand(card);
	}

}
