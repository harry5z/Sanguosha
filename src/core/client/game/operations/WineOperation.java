package core.client.game.operations;

import cards.Card;
import commands.Command;
import commands.game.server.ingame.UseWineInGameServerCommand;

public class WineOperation extends SimpleCardOperation {

	@Override
	protected Command<?> getCommand(Card card) {
		return new UseWineInGameServerCommand(card);
	}
	
	
}
