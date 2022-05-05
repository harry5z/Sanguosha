package core.client.game.operations.basics;

import cards.Card;
import cards.basics.Dodge;
import commands.game.server.ingame.DodgeReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.operations.AbstractSingleCardReactionOperation;

public class DodgeReactionOperation extends AbstractSingleCardReactionOperation {

	public DodgeReactionOperation(String message) {
		super(message);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return card instanceof Dodge;
	}

	@Override
	protected void onLoadedCustom() {
		
	}

	@Override
	protected void onUnloadedCustom() {
		
	}

	@Override
	protected boolean isCancelEnabled() {
		return true;
	}
	
	@Override
	protected InGameServerCommand getCommandOnCancel() {
		return new DodgeReactionInGameServerCommand(null);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new DodgeReactionInGameServerCommand(this.getFirstCardUI().getCard());
	}

}
