package core.client.game.operations;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import ui.game.interfaces.CardUI;

public abstract class AbstractCardReactionOperation implements Operation {

	protected GamePanel panel;
	protected CardUI card;
	private final String message;
	
	public AbstractCardReactionOperation(String message) {
		this.message = message;
	}
	
	@Override
	public void onConfirmed() {
		this.onDeactivated();
		this.panel.getChannel().send(this.getCommand(this.card.getCard()));
	}
	
	@Override
	public void onCanceled() {
		if (this.card != null) {
			this.card.setActivated(false);
			this.panel.getGameUI().setConfirmEnabled(false);
			this.card = null;
			if (!this.isCancelEnabled()) {
				// Cancel disabled when no card selected
				this.panel.getGameUI().setCancelEnabled(false);
			}
		} else {
			this.onDeactivated();
			this.panel.getChannel().send(this.getCommand(null));
		}
	}
	
	@Override
	public void onDeactivated() {
		if (this.card != null) {
			this.card.setActivated(false);
		}
		this.panel.getGameUI().setConfirmEnabled(false);
		this.panel.getGameUI().setCancelEnabled(false);
		for (CardUI ui : this.panel.getGameUI().getCardRackUI().getCardUIs()) {
			ui.setActivatable(false);
		}
		this.panel.getGameUI().clearMessage();
		this.panel.popOperation();
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		if (this.card == null) {
			this.card = card;
			this.card.setActivated(true);
			this.panel.getGameUI().setConfirmEnabled(true);
			if (!this.isCancelEnabled()) {
				// Cancel enabled when card is selected
				this.panel.getGameUI().setCancelEnabled(true);
			}
		} else if (this.card == card) {
			this.card.setActivated(false);
			this.card = null;
			this.panel.getGameUI().setConfirmEnabled(false);
		} else {
			this.card.setActivated(false);
			card.setActivated(true);
			this.card = card;
			if (!this.isCancelEnabled()) {
				// Cancel disabled when no card selected
				this.panel.getGameUI().setCancelEnabled(false);
			}
		}
	}

	@Override
	public void onActivated(GamePanel panel) {
		this.panel = panel;
		panel.getGameUI().setMessage(this.message);
		for (CardUI cardUI : panel.getGameUI().getCardRackUI().getCardUIs()) {
			if (this.isCardActivatable(cardUI.getCard())) {
				cardUI.setActivatable(true);
			}
		}
		if (this.isCancelEnabled()) {
			// TODO activate possible skills
			panel.getGameUI().setCancelEnabled(true);
		}
	}
	
	protected abstract boolean isCancelEnabled();
	
	protected abstract boolean isCardActivatable(Card card);
	
	protected abstract InGameServerCommand getCommand(Card card);
}
