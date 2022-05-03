package core.client.game.operations;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import ui.game.interfaces.CardUI;

/**
 * A generic Operation for when the player is expected to use a card to react
 * to some event (e.g. use Dodge/Neutralization)
 * 
 * @author Harry
 *
 */
public abstract class AbstractCardReactionOperation extends AbstractOperation {

	protected CardUI cardSelected;
	private final String message;
	
	public AbstractCardReactionOperation(String message) {
		this.message = message;
	}
	
	@Override
	public final void onCardClicked(CardUI card) {
		if (this.cardSelected == null) { // Selecting a new card
			this.cardSelected = card;
			this.cardSelected.setActivated(true);
			this.panel.getGameUI().setConfirmEnabled(true);
			// Cancel is enabled when a card is selected
			this.panel.getGameUI().setCancelEnabled(true);
		} else if (this.cardSelected == card) { // Unselecting the current card
			this.cardSelected.setActivated(false);
			this.cardSelected = null;
			this.panel.getGameUI().setConfirmEnabled(false);
			if (!this.isCancelAllowed()) {
				// Cancel is disabled when no card is selected
				this.panel.getGameUI().setCancelEnabled(false);
			}
		} else { // Select another card
			this.cardSelected.setActivated(false);
			card.setActivated(true);
			this.cardSelected = card;
		}
	}
	
	@Override
	public final void onConfirmed() {
		super.onConfirmed();
		this.panel.getChannel().send(this.getCommand(this.cardSelected.getCard()));
	}
	
	@Override
	public final void onCanceled() {
		if (this.cardSelected != null) { // Unselect the current card
			this.cardSelected.setActivated(false);
			this.panel.getGameUI().setConfirmEnabled(false);
			this.cardSelected = null;
			if (!this.isCancelAllowed()) {
				// Cancel is disabled when no card is selected
				this.panel.getGameUI().setCancelEnabled(false);
			}
		} else { // Give up card reaction
			this.onUnloaded();
			this.onDeactivated();
			this.panel.getChannel().send(this.getCommand(null));
		}
	}
	
	@Override
	public final void onLoaded() {
		for (CardUI cardUI : panel.getGameUI().getCardRackUI().getCardUIs()) {
			if (this.isCardActivatable(cardUI.getCard())) {
				cardUI.setActivatable(true);
			}
		}
		if (this.isCancelAllowed()) {
			this.panel.getGameUI().setCancelEnabled(true);
		}
		this.panel.getGameUI().setMessage(this.message);
		this.onLoadedCustom();
	}
	
	@Override
	public final void onUnloaded() {
		if (this.cardSelected != null) {
			this.cardSelected.setActivated(false);
		}
		this.panel.getGameUI().setConfirmEnabled(false);
		this.panel.getGameUI().setCancelEnabled(false);
		this.panel.getGameUI().getCardRackUI().getCardUIs().forEach(ui -> ui.setActivatable(false));
		this.panel.getGameUI().clearMessage();
		this.onUnloadedCustom();
	}
	
	protected abstract boolean isCancelAllowed();
	
	protected abstract boolean isCardActivatable(Card card);
	
	protected abstract void onLoadedCustom();
	
	protected abstract void onUnloadedCustom();
	
	protected abstract InGameServerCommand getCommand(Card card);
}
