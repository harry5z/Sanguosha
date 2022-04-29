package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateBrotherhoodInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;
import ui.game.interfaces.Activatable;

public class BrotherhoodOperation extends AbstractCardUsageOperation {

	public BrotherhoodOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new InitiateBrotherhoodInGameServerCommand(card);
	}

}
