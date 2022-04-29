package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateBarbarianInvasionInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;
import ui.game.interfaces.Activatable;

public class BarbarianInvasionOperation extends AbstractCardUsageOperation {

	public BarbarianInvasionOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new InitiateBarbarianInvasionInGameServerCommand(card);
	}

}
