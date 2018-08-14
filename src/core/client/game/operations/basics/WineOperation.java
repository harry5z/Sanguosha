package core.client.game.operations.basics;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.UseWineInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class WineOperation extends AbstractCardUsageOperation {

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new UseWineInGameServerCommand(card);
	}
	
	
}
