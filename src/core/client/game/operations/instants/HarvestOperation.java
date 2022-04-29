package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateHarvestInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;
import ui.game.interfaces.Activatable;

public class HarvestOperation extends AbstractCardUsageOperation {

	public HarvestOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new InitiateHarvestInGameServerCommand(card);
	}

}
