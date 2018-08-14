package core.client.game.operations.basics;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.UsePeachInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class PeachOperation extends AbstractCardUsageOperation {

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new UsePeachInGameServerCommand(card);
	}

}
