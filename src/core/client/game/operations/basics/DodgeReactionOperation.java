package core.client.game.operations.basics;

import cards.Card;
import cards.basics.Dodge;
import commands.game.server.ingame.DodgeReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.operations.AbstractCardReactionOperation;

public class DodgeReactionOperation extends AbstractCardReactionOperation {

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Dodge;
	}
	
	@Override
	protected boolean isCancelEnabled() {
		return true;
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new DodgeReactionInGameServerCommand(card);
	}

}
