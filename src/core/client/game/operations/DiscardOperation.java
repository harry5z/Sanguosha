package core.client.game.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import commands.game.server.DiscardGameServerCommand;
import net.client.GamePanel;
import ui.game.Activatable;
import ui.game.CardGui;
import ui.game.GamePanelUI;

public class DiscardOperation implements Operation {
	
	private GamePanel panel;
	private List<CardGui> cards = new ArrayList<>();
	private final int amount;
	
	public DiscardOperation(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void onConfirmed() {
		for(CardGui cardUI : panel.getContent().getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(false);
		}
		panel.getContent().setConfirmEnabled(false);
		panel.getChannel().send(new DiscardGameServerCommand(cards.stream().map(ui -> ui.getCard()).collect(Collectors.toList())));
	}

	@Override
	public void onCardClicked(CardGui card) {
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
		GamePanelUI panelUI = panel.getContent();
		panelUI.showCountdownBar();
		for(CardGui cardUI : panelUI.getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(true);
		}
	}

}
