package core.client.game.operations;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.player.PlayerInfo;
import ui.game.CardGui;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;

public abstract class AbstractCardUsageOperation implements Operation {

	private CardGui card;
	private GamePanel panel;
	protected PlayerInfo source;

	@Override
	public final void onCanceled() {
		this.onDeactivated();
	}

	@Override
	public final void onConfirmed() {
		this.onDeactivated();
		panel.getCurrentOperation().onConfirmed();
		panel.getChannel().send(getCommand(card == null ? null : card.getCard()));
	}
	
	@Override
	public final void onCardClicked(CardUI card) {
		this.onDeactivated();
		if (card != this.card) {
			panel.getCurrentOperation().onCardClicked(card);
		}
	}

	@Override
	public final void onEnded() {
		this.onDeactivated();
		panel.getCurrentOperation().onEnded();
	}

	@Override
	public final void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		this.card = (CardGui) source;
		ClientGameUI panelUI = panel.getContent();
		panelUI.setMessage("Use " + this.card.getCard() + "?");
		this.source = panelUI.getSelf().getPlayerInfo();
		panel.getContent().setConfirmEnabled(true);
		panel.getContent().setCancelEnabled(true);
	}
	
	@Override
	public void onDeactivated() {
		card.setActivated(false);
		panel.getContent().setConfirmEnabled(false);
		panel.getContent().setCancelEnabled(false);
		panel.getContent().clearMessage();
		panel.popOperation();
	}
	
	protected abstract InGameServerCommand getCommand(Card card);

}
