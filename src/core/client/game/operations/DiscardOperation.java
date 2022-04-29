package core.client.game.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import commands.game.server.ingame.DiscardInGameServerCommand;
import core.client.GamePanel;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;

public class DiscardOperation implements Operation {
	
	private GamePanel panel;
	private List<CardUI> cards = new ArrayList<>();
	private final int amount;
	
	public DiscardOperation(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void onConfirmed() {
		for(CardUI cardUI : panel.getContent().getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(false);
		}
		panel.getContent().setConfirmEnabled(false);
		panel.getContent().clearMessage();
		panel.getChannel().send(new DiscardInGameServerCommand(cards.stream().map(ui -> ui.getCard()).collect(Collectors.toList())));
	}

	@Override
	public void onCardClicked(CardUI card) {
		if (cards.remove(card)) {
			card.setActivated(false);
			if (cards.size() == 0) {
				panel.getContent().setConfirmEnabled(false);
			}
		} else {
			card.setActivated(true);
			if (cards.size() == 0) {
				panel.getContent().setConfirmEnabled(true);
			}
			cards.add(card);
			if (cards.size() > amount) {
				cards.get(0).setActivated(false);
				cards.remove(0);
			}
		}
	}

	@Override
	public void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		ClientGameUI panelUI = panel.getContent();
		panelUI.setMessage("Select " + this.amount + " cards to discard");
		panelUI.showCountdownBar();
		for(CardUI cardUI : panelUI.getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(true);
		}
	}

}
