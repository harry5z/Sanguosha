package core.client.game.operations.basics;

import cards.Card;
import commands.Command;
import commands.game.server.ingame.UseWineInGameServerCommand;
import core.client.game.operations.SimpleCardOperation;

public class WineOperation extends SimpleCardOperation {

	@Override
	protected Command<?> getCommand(Card card) {
		return new UseWineInGameServerCommand(card);
	}
	
	
}
