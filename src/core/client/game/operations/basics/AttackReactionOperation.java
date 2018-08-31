package core.client.game.operations.basics;

import cards.Card;
import cards.basics.Attack;
import commands.game.server.ingame.AttackReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.operations.AbstractCardReactionOperation;

public class AttackReactionOperation extends AbstractCardReactionOperation {

	public AttackReactionOperation(String message) {
		super(message);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Attack;
	}
	
	@Override
	protected boolean isCancelEnabled() {
		return true;
	}

	@Override
	protected InGameServerCommand getCommand(Card card) {
		return new AttackReactionInGameServerCommand(card);
	}

}
