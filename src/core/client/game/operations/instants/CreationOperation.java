package core.client.game.operations.instants;

import cards.Card;
import commands.Command;
import commands.game.server.ingame.InitiateCreationInGameServerCommand;
import core.client.game.operations.SimpleCardOperation;

public class CreationOperation extends SimpleCardOperation {

	@Override
	protected Command<?> getCommand(Card card) {
		return new InitiateCreationInGameServerCommand(card);
	}

}
