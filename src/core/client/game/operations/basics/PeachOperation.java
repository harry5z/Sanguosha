package core.client.game.operations.basics;

import cards.Card;
import commands.game.server.GameServerCommand;
import commands.game.server.ingame.UsePeachInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class PeachOperation extends AbstractCardUsageOperation {

	@Override
	protected GameServerCommand getCommand(Card card) {
		return new UsePeachInGameServerCommand(card);
	}

}
