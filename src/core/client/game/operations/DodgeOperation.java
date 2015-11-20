package core.client.game.operations;

import cards.basics.Dodge;
import commands.game.server.DodgeReactionInGameServerCommand;
import net.client.GamePanel;
import ui.game.Activatable;
import ui.game.CardGui;

public class DodgeOperation implements Operation {
	
	private GamePanel panel;
	private CardGui dodge;
	
	@Override
	public void onConfirmed() {
		dodge.setActivated(false);
		panel.getContent().setConfirmEnabled(false);
		panel.getContent().setCancelEnabled(false);
		for (CardGui ui : panel.getContent().getCardRackUI().getCardUIs()) {
			ui.setActivatable(false);
		}
		panel.getChannel().send(new DodgeReactionInGameServerCommand(dodge.getCard()));
	}
	
	@Override
	public void onCanceled() {
		if (dodge != null) {
			dodge.setActivated(false);
			panel.getContent().setConfirmEnabled(false);
			dodge = null;
		} else {
			panel.getContent().setConfirmEnabled(false);
			panel.getContent().setCancelEnabled(false);
			for (CardGui ui : panel.getContent().getCardRackUI().getCardUIs()) {
				ui.setActivatable(false);
			}
			panel.getChannel().send(new DodgeReactionInGameServerCommand(null));
		}
	}
	
	@Override
	public void onCardClicked(CardGui card) {
		if (dodge == null) {
			dodge = card;
			dodge.setActivated(true);
			panel.getContent().setConfirmEnabled(true);
		} else if (dodge == card) {
			dodge.setActivated(false);
			dodge = null;
			panel.getContent().setConfirmEnabled(false);
		} else {
			dodge.setActivated(false);
			card.setActivated(true);
			dodge = card;
		}
	}

	@Override
	public void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		for (CardGui cardUI: panel.getContent().getCardRackUI().getCardUIs()) {
			if (cardUI.getCard() instanceof Dodge) {
				cardUI.setActivatable(true);
			}
		}
		// TODO activate possible skills
		panel.getContent().setCancelEnabled(true);
	}

}
