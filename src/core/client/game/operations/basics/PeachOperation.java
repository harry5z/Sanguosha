package core.client.game.operations.basics;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.UsePeachInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;
import ui.game.interfaces.Activatable;

public class PeachOperation extends AbstractCardUsageOperation {

	public PeachOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new UsePeachInGameServerCommand(card);
	}

}
