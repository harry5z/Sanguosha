package core.client.game.operations.mechanics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cards.Card;
import commands.server.ingame.DiscardInGameServerCommand;
import commands.server.ingame.InGameServerCommand;
import core.client.game.operations.AbstractMultiCardNoTargetReactionOperation;
import ui.game.interfaces.CardUI;

public class DiscardOperation extends AbstractMultiCardNoTargetReactionOperation {
	
	public DiscardOperation(int amount) {
		super(amount);
	}

	@Override
	protected boolean isConfirmEnabled() {
		return this.cards.size() == this.maxCards;
	}

	@Override
	protected boolean isCancelEnabled() {
		return this.cards.size() > 0;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return true;
	}

	@Override
	protected String getMessage() {
		return "Select " + this.maxCards + " cards to discard";
	}

	@Override
	protected void onLoadedCustom() {
		
	}

	@Override
	protected void onUnloadedCustom() {
		
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		List<Card> discardedCards = new ArrayList<>();
		Iterator<CardUI> it = this.cards.keySet().iterator();
		while (it.hasNext()) {
			discardedCards.add(it.next().getCard());
		}
		return new DiscardInGameServerCommand(discardedCards);
	}

	@Override
	protected InGameServerCommand getCommandOnInaction() {
		return null;
	}

}
