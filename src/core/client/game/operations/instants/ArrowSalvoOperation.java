package core.client.game.operations.instants;

import cards.Card;
import commands.Command;
import commands.game.server.ingame.InitiateArrowSalvoInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class ArrowSalvoOperation extends AbstractCardUsageOperation {

	@Override
	protected Command<?> getCommand(Card card) {
		return new InitiateArrowSalvoInGameServerCommand(this.source, card);
	}

}
