package core.client.game.operations.basics;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.AbstractCardReactionOperation;
import core.player.PlayerCardZone;

public class ShowCardReactionOperation extends AbstractCardReactionOperation {

	@Override
	protected boolean isCardActivatable(Card card) {
		// any card can be shown
		return true;
	}
	
	@Override
	protected boolean isCancelEnabled() {
		return false;
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new PlayerCardSelectionInGameServerCommand(card, PlayerCardZone.HAND);
	}
	
}
