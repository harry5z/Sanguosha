package core.client.game.operations;

import cards.Card;
import commands.Command;
import net.client.GamePanel;
import ui.game.Activatable;
import ui.game.CardGui;

public abstract class SimpleCardOperation implements Operation {

	private CardGui card;
	private GamePanel panel;

	@Override
	public final void onCanceled() {
		card.setActivated(false);
		panel.getContent().setConfirmEnabled(false);
		panel.getContent().setCancelEnabled(false);
		panel.popOperation();
	}

	@Override
	public final void onConfirmed() {
		onCanceled();
		panel.getChannel().send(getCommand(card == null ? null : card.getCard()));
	}
	
	protected abstract Command<?> getCommand(Card card);

	@Override
	public final void onCardClicked(CardGui card) {
		onCanceled();
		if (card != this.card) {
			panel.getCurrentOperation().onCardClicked(card);
		}
	}

	@Override
	public final void onEnded() {
		onCanceled();
		panel.getCurrentOperation().onEnded();
	}

	@Override
	public final void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		this.card = (CardGui) source;
		panel.getContent().setConfirmEnabled(true);
		panel.getContent().setCancelEnabled(true);
	}

}
