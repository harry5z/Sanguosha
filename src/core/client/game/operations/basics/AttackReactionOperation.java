package core.client.game.operations.basics;

import cards.Card;
import cards.basics.Attack;
import commands.game.server.ingame.AttackReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.operations.AbstractCardReactionOperation;

public class AttackReactionOperation extends AbstractCardReactionOperation {

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Attack;
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new AttackReactionInGameServerCommand(card);
	}

}
