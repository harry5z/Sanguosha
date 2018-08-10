package core.client.game.operations.instants;

import cards.Card;
import commands.Command;
import commands.game.server.ingame.InitiateBrotherhoodInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class BrotherhoodOperation extends AbstractCardUsageOperation {

	@Override
	protected Command<?> getCommand(Card card) {
		return new InitiateBrotherhoodInGameServerCommand(this.source, card);
	}

}
