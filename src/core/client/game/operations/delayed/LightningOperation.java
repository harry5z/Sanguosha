package core.client.game.operations.delayed;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.UseLightningInGameServerCommand;
import core.client.game.operations.AbstractCardUsageOperation;
import ui.game.interfaces.Activatable;

public class LightningOperation extends AbstractCardUsageOperation {

	public LightningOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new UseLightningInGameServerCommand(card);
	}

}
