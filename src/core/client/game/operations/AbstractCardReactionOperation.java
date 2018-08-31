package core.client.game.operations;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;

public abstract class AbstractCardReactionOperation implements Operation {

	protected GamePanel<? extends Hero> panel;
	protected CardUI card;
	private final String message;
	
	public AbstractCardReactionOperation(String message) {
		this.message = message;
	}
	
	@Override
	public void onConfirmed() {
		this.card.setActivated(false);
		this.panel.getContent().setConfirmEnabled(false);
		this.panel.getContent().setCancelEnabled(false);
		for (CardUI ui : this.panel.getContent().getCardRackUI().getCardUIs()) {
			ui.setActivatable(false);
		}
		this.panel.popOperation();
		this.panel.getContent().clearMessage();
		this.panel.getChannel().send(this.getCommand(this.card.getCard()));
	}
	
	@Override
	public void onCanceled() {
		if (this.card != null) {
			this.card.setActivated(false);
			this.panel.getContent().setConfirmEnabled(false);
			this.card = null;
			if (!this.isCancelEnabled()) {
				// Cancel disabled when no card selected
				this.panel.getContent().setCancelEnabled(false);
			}
		} else {
			this.panel.getContent().setConfirmEnabled(false);
			this.panel.getContent().setCancelEnabled(false);
			for (CardUI ui : this.panel.getContent().getCardRackUI().getCardUIs()) {
				ui.setActivatable(false);
			}
			this.panel.popOperation();
			this.panel.getContent().clearMessage();
			this.panel.getChannel().send(this.getCommand(null));
		}
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		if (this.card == null) {
			this.card = card;
			this.card.setActivated(true);
			this.panel.getContent().setConfirmEnabled(true);
			if (!this.isCancelEnabled()) {
				// Cancel enabled when card is selected
				this.panel.getContent().setCancelEnabled(true);
			}
		} else if (this.card == card) {
			this.card.setActivated(false);
			this.card = null;
			this.panel.getContent().setConfirmEnabled(false);
		} else {
			this.card.setActivated(false);
			card.setActivated(true);
			this.card = card;
			if (!this.isCancelEnabled()) {
				// Cancel disabled when no card selected
				this.panel.getContent().setCancelEnabled(false);
			}
		}
	}

	@Override
	public void onActivated(GamePanel<? extends Hero> panel, Activatable source) {
		this.panel = panel;
		panel.getContent().setMessage(this.message);
		for (CardUI cardUI : panel.getContent().getCardRackUI().getCardUIs()) {
			if (this.isCardActivatable(cardUI.getCard())) {
				cardUI.setActivatable(true);
			}
		}
		if (this.isCancelEnabled()) {
			// TODO activate possible skills
			panel.getContent().setCancelEnabled(true);
		}
	}
	
	protected abstract boolean isCancelEnabled();
	
	protected abstract boolean isCardActivatable(Card card);
	
	protected abstract InGameServerCommand getCommand(Card card);
}
