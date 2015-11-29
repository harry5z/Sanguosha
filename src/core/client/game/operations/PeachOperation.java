package core.client.game.operations;

import cards.Card;
import commands.Command;
import commands.game.server.ingame.UsePeachInGameServerCommand;

public class PeachOperation extends SimpleCardOperation {

	@Override
	protected Command<?> getCommand(Card card) {
		return new UsePeachInGameServerCommand(card);
	}

}
