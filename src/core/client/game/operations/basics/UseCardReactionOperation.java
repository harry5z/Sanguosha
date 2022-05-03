package core.client.game.operations.basics;

import java.util.Collection;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.PlayerCardSelectionInGameServerCommand;
import core.client.game.operations.AbstractCardReactionOperation;
import core.event.game.basic.RequestUseCardEvent.RequestUseCardPredicate;
import core.player.PlayerCardZone;

public class UseCardReactionOperation extends AbstractCardReactionOperation {
	
	private final Collection<RequestUseCardPredicate> predicates;
	
	public UseCardReactionOperation(
		String message,
		Collection<RequestUseCardPredicate> predicates
	) {
		super(message);
		this.predicates = predicates;
	}

	@Override
	protected boolean isCancelAllowed() {
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
	protected InGameServerCommand getCommand(Card card) {
		return new PlayerCardSelectionInGameServerCommand(card, PlayerCardZone.HAND);
	}

	@Override
	protected void onLoadedCustom() {
		
	}

	@Override
	protected void onUnloadedCustom() {
		
	}

}
