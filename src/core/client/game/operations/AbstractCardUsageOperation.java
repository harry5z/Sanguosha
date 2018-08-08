package core.client.game.operations;

import cards.Card;
import commands.Command;
import core.client.GamePanel;
import core.heroes.Hero;
import core.player.PlayerInfo;
import ui.game.CardGui;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;

public abstract class AbstractCardUsageOperation implements Operation {

	private CardGui card;
	private GamePanel<? extends Hero> panel;
	protected PlayerInfo source;

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
		panel.getCurrentOperation().onConfirmed();
		panel.getChannel().send(getCommand(card == null ? null : card.getCard()));
	}
	
	protected abstract Command<?> getCommand(Card card);

	@Override
	public final void onCardClicked(CardUI card) {
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
	public final void onActivated(GamePanel<? extends Hero> panel, Activatable source) {
		this.panel = panel;
		this.card = (CardGui) source;
		ClientGameUI<? extends Hero> panelUI = panel.getContent();
		this.source = panelUI.getSelf().getPlayerInfo();
		panel.getContent().setConfirmEnabled(true);
		panel.getContent().setCancelEnabled(true);
	}

}
