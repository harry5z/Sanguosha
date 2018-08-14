package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateArrowSalvoInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class ArrowSalvoOperation extends AbstractCardUsageOperation {

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new InitiateArrowSalvoInGameServerCommand(card);
	}

}
