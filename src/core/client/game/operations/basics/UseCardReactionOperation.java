package core.client.game.operations.basics;

import cards.Card;
import commands.game.client.RequestUseCardGameUIClientCommand.RequestUseCardFilter;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.AbstractSingleCardReactionOperation;
import core.player.PlayerCardZone;

public class UseCardReactionOperation extends AbstractSingleCardReactionOperation {
	
	private final RequestUseCardFilter predicate;
	
	public UseCardReactionOperation(String message, RequestUseCardFilter predicate) {
		super(message);
		this.predicate = predicate;
	}

	@Override
	protected boolean isCancelEnabled() {
		return true;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return predicate.test(card);
	}

	@Override
	protected void onLoadedCustom() {
		
	}

	@Override
	protected void onUnloadedCustom() {
		
	}

	@Override
	protected InGameServerCommand getCommandOnInaction() {
		return new PlayerCardSelectionInGameServerCommand(null, PlayerCardZone.HAND);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new PlayerCardSelectionInGameServerCommand(
			this.getFirstCardUI().getCard(),
			PlayerCardZone.HAND
		);
	}

}
