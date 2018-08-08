package core.client.game.operations;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;

public abstract class AbstractCardReactionOperation implements Operation {

	private GamePanel<? extends Hero> panel;
	private CardUI card;
	
	@Override
	public void onConfirmed() {
		this.card.setActivated(false);
		this.panel.getContent().setConfirmEnabled(false);
		this.panel.getContent().setCancelEnabled(false);
		for (CardUI ui : this.panel.getContent().getCardRackUI().getCardUIs()) {
			ui.setActivatable(false);
		}
		this.panel.popOperation();
		this.panel.getChannel().send(this.getCommand(this.card.getCard()));
	}
	
	@Override
	public void onCanceled() {
		if (this.card != null) {
			this.card.setActivated(false);
			this.panel.getContent().setConfirmEnabled(false);
			this.card = null;
		} else {
			this.panel.getContent().setConfirmEnabled(false);
			this.panel.getContent().setCancelEnabled(false);
			for (CardUI ui : this.panel.getContent().getCardRackUI().getCardUIs()) {
				ui.setActivatable(false);
			}
			this.panel.popOperation();
			this.panel.getChannel().send(this.getCommand(null));
		}
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		if (this.card == null) {
			this.card = card;
			this.card.setActivated(true);
			this.panel.getContent().setConfirmEnabled(true);
		} else if (this.card == card) {
			this.card.setActivated(false);
			this.card = null;
			this.panel.getContent().setConfirmEnabled(false);
		} else {
			this.card.setActivated(false);
			card.setActivated(true);
			this.card = card;
		}
	}

	@Override
	public void onActivated(GamePanel<? extends Hero> panel, Activatable source) {
		this.panel = panel;
		for (CardUI cardUI : panel.getContent().getCardRackUI().getCardUIs()) {
			if (this.isCardActivatable(cardUI.getCard())) {
				cardUI.setActivatable(true);
			}
		}
		// TODO activate possible skills
		panel.getContent().setCancelEnabled(true);
	}
	
	protected abstract boolean isCardActivatable(Card card);
	
	protected abstract InGameServerCommand getCommand(Card card);
}
