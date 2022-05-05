package core.client.game.operations.basics;

import java.util.Collection;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.AbstractSingleCardReactionOperation;
import core.event.game.basic.RequestUseCardEvent.RequestUseCardPredicate;
import core.player.PlayerCardZone;

public class UseCardReactionOperation extends AbstractSingleCardReactionOperation {
	
	private final Collection<RequestUseCardPredicate> predicates;
	
	public UseCardReactionOperation(
		String message,
		Collection<RequestUseCardPredicate> predicates
	) {
		super(message);
		this.predicates = predicates;
	}

	@Override
	protected boolean isCancelEnabled() {
		return true;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		for (RequestUseCardPredicate predicate : this.predicates) {
			if (!predicate.test(card)) {
				return false;
			}
		}
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
