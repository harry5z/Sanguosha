package core.client.game.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import commands.game.server.ingame.DiscardInGameServerCommand;
import core.client.GamePanel;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;

public class DiscardOperation implements Operation {
	
	private GamePanel panel;
	private List<CardUI> cards = new ArrayList<>();
	private final int amount;
	
	public DiscardOperation(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void onConfirmed() {
		for(CardUI cardUI : panel.getGameUI().getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(false);
		}
		panel.getGameUI().setConfirmEnabled(false);
		panel.getGameUI().clearMessage();
		panel.getChannel().send(new DiscardInGameServerCommand(cards.stream().map(ui -> ui.getCard()).collect(Collectors.toList())));
	}

	@Override
	public void onCardClicked(CardUI card) {
		if (cards.remove(card)) {
			card.setActivated(false);
			if (cards.size() == 0) {
				panel.getGameUI().setConfirmEnabled(false);
			}
		} else {
			card.setActivated(true);
			if (cards.size() == 0) {
				panel.getGameUI().setConfirmEnabled(true);
			}
			cards.add(card);
			if (cards.size() > amount) {
				cards.get(0).setActivated(false);
				cards.remove(0);
			}
		}
	}

	@Override
	public void onActivated(GamePanel panel) {
		this.panel = panel;
		GameUI panelUI = panel.getGameUI();
		panelUI.setMessage("Select " + this.amount + " cards to discard");
		panelUI.showCountdownBar();
		for(CardUI cardUI : panelUI.getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(true);
		}
	}

}
