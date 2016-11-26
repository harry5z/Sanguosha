package core.client.game.operations.basics;

import cards.Card;
import commands.Command;
import commands.game.server.ingame.UsePeachInGameServerCommand;
import core.client.game.operations.SimpleCardOperation;

public class PeachOperation extends SimpleCardOperation {

	@Override
	protected Command<?> getCommand(Card card) {
		return new UsePeachInGameServerCommand(card);
	}

}
