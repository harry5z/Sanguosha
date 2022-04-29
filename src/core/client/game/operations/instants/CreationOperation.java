package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateCreationInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;
import ui.game.interfaces.Activatable;

public class CreationOperation extends AbstractCardUsageOperation {

	public CreationOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new InitiateCreationInGameServerCommand(card);
	}

}
