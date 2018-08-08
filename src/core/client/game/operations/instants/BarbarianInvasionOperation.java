package core.client.game.operations.instants;

import cards.Card;
import commands.Command;
import commands.game.server.ingame.InitiateBarbarianInvasionInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class BarbarianInvasionOperation extends AbstractCardUsageOperation {

	@Override
	protected Command<?> getCommand(Card card) {
		return new InitiateBarbarianInvasionInGameServerCommand(this.source, card);
	}

}
