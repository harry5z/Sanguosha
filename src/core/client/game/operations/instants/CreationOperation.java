package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.GameServerCommand;
import commands.game.server.ingame.InitiateCreationInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class CreationOperation extends AbstractCardUsageOperation {

	@Override
	protected GameServerCommand getCommand(Card card) {
		return new InitiateCreationInGameServerCommand(card);
	}

}
