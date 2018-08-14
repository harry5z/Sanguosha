package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.GameServerCommand;
import commands.game.server.ingame.InitiateBarbarianInvasionInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class BarbarianInvasionOperation extends AbstractCardUsageOperation {

	@Override
	protected GameServerCommand getCommand(Card card) {
		return new InitiateBarbarianInvasionInGameServerCommand(card);
	}

}
