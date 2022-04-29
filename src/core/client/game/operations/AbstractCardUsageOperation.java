package core.client.game.operations;

import cards.Card;
import commands.game.server.ingame.InGameServerCommand;
import core.client.GamePanel;
import core.player.PlayerInfo;
import ui.game.CardGui;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;

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
		panel.getGameUI().setMessage("Use " + this.card.getCard() + "?");
		this.source = panel.getGameState().getSelf().getPlayerInfo();
		panel.getGameUI().setConfirmEnabled(true);
		panel.getGameUI().setCancelEnabled(true);
	}
	
	@Override
	public void onDeactivated() {
		card.setActivated(false);
		panel.getGameUI().setConfirmEnabled(false);
		panel.getGameUI().setCancelEnabled(false);
		panel.getGameUI().clearMessage();
		panel.popOperation();
	}
	
	protected abstract InGameServerCommand getCommand(Card card);

}
