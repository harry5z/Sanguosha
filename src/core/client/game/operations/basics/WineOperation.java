package core.client.game.operations.basics;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.UseWineInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;
import ui.game.interfaces.Activatable;

public class WineOperation extends AbstractCardUsageOperation {

	public WineOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new UseWineInGameServerCommand(card);
	}
	
}
