package core.client.game.operations.basics;

import cards.Card;
import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.AbstractSingleCardReactionOperation;
import core.player.PlayerCardZone;

public class ShowCardReactionOperation extends AbstractSingleCardReactionOperation {

	public ShowCardReactionOperation(String message) {
		super(message);
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return true;
	}
	
	@Override
	protected void onLoadedCustom() {
		
	}

	@Override
	protected void onUnloadedCustom() {
		
	}

	@Override
	protected InGameServerCommand getCommandOnInaction() {
		// Cannot cancel
		return null;
	}

	@Override
	protected boolean isCancelEnabled() {
		return this.cards.size() > 0;
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new PlayerCardSelectionInGameServerCommand(
			this.getFirstCardUI().getCard(),
			PlayerCardZone.HAND
		);
	}
	
}
