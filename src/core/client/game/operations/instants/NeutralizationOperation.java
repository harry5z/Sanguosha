package core.client.game.operations.instants;

import cards.specials.instant.Neutralization;
import commands.game.server.ingame.NeutralizationReactionInGameServerCommand;
import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.heroes.Hero;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;

public class NeutralizationOperation implements Operation {
	
	private GamePanel<? extends Hero> panel;
	private CardUI neutralization;

	@Override
	public void onConfirmed() {
		neutralization.setActivated(false);
		panel.getContent().setConfirmEnabled(false);
		panel.getContent().setCancelEnabled(false);
		for (CardUI ui : panel.getContent().getCardRackUI().getCardUIs()) {
			ui.setActivatable(false);
		}
		panel.getChannel().send(new NeutralizationReactionInGameServerCommand(panel.getContent().getSelf().getPlayerInfo(), neutralization.getCard()));
	}
	
	@Override
	public void onCanceled() {
		if (neutralization != null) {
			neutralization.setActivated(false);
			panel.getContent().setConfirmEnabled(false);
			neutralization = null;
		} else {
			panel.getContent().setConfirmEnabled(false);
			panel.getContent().setCancelEnabled(false);
			for (CardUI ui : panel.getContent().getCardRackUI().getCardUIs()) {
				ui.setActivatable(false);
			}
			panel.getChannel().send(new NeutralizationReactionInGameServerCommand(null, null));
		}
	}
	
	@Override
	public void onCardClicked(CardUI card) {
		if (neutralization == null) {
			neutralization = card;
			neutralization.setActivated(true);
			panel.getContent().setConfirmEnabled(true);
		} else if (neutralization == card) {
			neutralization.setActivated(false);
			neutralization = null;
			panel.getContent().setConfirmEnabled(false);
		} else {
			neutralization.setActivated(false);
			card.setActivated(true);
			neutralization = card;
		}
	}
	
	@Override
	public void onEnded() {
		if (neutralization != null) {
			neutralization.setActivated(false);
			neutralization = null;
		}
		panel.getContent().setConfirmEnabled(false);
		panel.getContent().setCancelEnabled(false);
		for (CardUI ui : panel.getContent().getCardRackUI().getCardUIs()) {
			ui.setActivatable(false);
		}
	}

	@Override
	public void onActivated(GamePanel<? extends Hero> panel, Activatable source) {
		this.panel = panel;
		if (panel.getCurrentOperation() instanceof NeutralizationOperation) {
			panel.getCurrentOperation().onEnded();
			panel.popOperation();
		}
		for (CardUI cardUI: panel.getContent().getCardRackUI().getCardUIs()) {
			if (cardUI.getCard() instanceof Neutralization) {
				cardUI.setActivatable(true);
			}
		}
		// TODO activate possible skills
		panel.getContent().setCancelEnabled(true);
	}
}
