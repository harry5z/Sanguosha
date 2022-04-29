package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateArrowSalvoInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;
import ui.game.interfaces.Activatable;

public class ArrowSalvoOperation extends AbstractCardUsageOperation {

	public ArrowSalvoOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new InitiateArrowSalvoInGameServerCommand(card);
	}

}
