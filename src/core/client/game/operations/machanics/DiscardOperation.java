package core.client.game.operations.machanics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import commands.game.server.ingame.DiscardInGameServerCommand;
import core.client.game.operations.AbstractOperation;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;

public class DiscardOperation extends AbstractOperation {
	
	private List<CardUI> cards = new ArrayList<>();
	private final int amount;
	
	public DiscardOperation(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void onConfirmed() {
		this.onUnloaded();
		this.onDeactivated();
		this.panel.getChannel().send(new DiscardInGameServerCommand(cards.stream().map(ui -> ui.getCard()).collect(Collectors.toList())));
	}

	@Override
	public void onCardClicked(CardUI card) {
		if (cards.remove(card)) { // unselecting a card
			card.setActivated(false);
			if (cards.size() == 0) {
				panel.getGameUI().setConfirmEnabled(false);
			}
		} else { // selecting a new card
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
	public void onLoaded() {
		GameUI panelUI = this.panel.getGameUI();
		panelUI.setMessage("Select " + this.amount + " cards to discard");
		for(CardUI cardUI : panelUI.getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(true);
		}
	}
	
	@Override
	public void onUnloaded() {
		for(CardUI cardUI : this.panel.getGameUI().getCardRackUI().getCardUIs()) {
			cardUI.setActivatable(false);
		}
		this.panel.getGameUI().setConfirmEnabled(false);
		this.panel.getGameUI().clearMessage();		
	}

}
