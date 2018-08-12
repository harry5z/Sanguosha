package core.client.game.operations.basics;

import cards.Card;
import commands.game.server.GameServerCommand;
import commands.game.server.ingame.UseWineInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class WineOperation extends AbstractCardUsageOperation {

	@Override
	protected GameServerCommand getCommand(Card card) {
		return new UseWineInGameServerCommand(card);
	}
	
	
}
