package core.client.game.operations;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import core.player.PlayerInfo;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;

/**
 * A generic target-less card usage action during a player's DEAL stage, e.g. Peach
 * 
 * @author Harry
 *
 */
public abstract class AbstractCardUsageOperation extends AbstractOperation {

	private final Activatable card;
	protected PlayerInfo source;
	
	public AbstractCardUsageOperation(Activatable card) {
		this.card = card;
	}

	@Override
	public final void onConfirmed() {
		super.onConfirmed();
		this.panel.getChannel().send(getCommand(card == null ? null : ((CardUI) card).getCard()));
	}
	
	@Override
	public final void onCardClicked(CardUI card) {
		// Only the currently selected card should be clickable by implementation
		// Behave as if the CANCEL button is pressed
		this.onCanceled();
	}

	@Override
	public final void onLoaded() {
		this.card.setActivatable(true);
		this.card.setActivated(true);
		this.panel.getGameUI().setConfirmEnabled(true);
		this.panel.getGameUI().setCancelEnabled(true);
		this.panel.getGameUI().setMessage("Use " + ((CardUI) this.card).getCard() + "?");
		this.source = this.panel.getGameState().getSelf().getPlayerInfo();
	}
	
	@Override
	public final void onUnloaded() {
		this.card.setActivatable(false);
		this.card.setActivated(false);
		this.panel.getGameUI().setConfirmEnabled(false);
		this.panel.getGameUI().setCancelEnabled(false);
		this.panel.getGameUI().clearMessage();
	}
	
	protected abstract InGameServerCommand getCommand(Card card);

}
