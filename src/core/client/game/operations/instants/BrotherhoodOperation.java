package core.client.game.operations.instants;

import cards.Card;
import commands.game.server.GameServerCommand;
import commands.game.server.ingame.InitiateBrotherhoodInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class BrotherhoodOperation extends AbstractCardUsageOperation {

	@Override
	protected GameServerCommand getCommand(Card card) {
		return new InitiateBrotherhoodInGameServerCommand(card);
	}

}
