package core.client.game.operations.delayed;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.UseLightningInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;

public class LightningOperation extends AbstractCardUsageOperation {

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new UseLightningInGameServerCommand(card);
	}

}
