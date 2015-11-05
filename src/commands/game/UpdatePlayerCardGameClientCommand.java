package commands.game;

import net.Connection;
import net.client.ClientUI;
import ui.CardRackGui;
import ui.PanelGui;
import cards.Card;

public class UpdatePlayerCardGameClientCommand implements GameClientCommand {
	private static final long serialVersionUID = 5370641268667157302L;

	private final Card card;
	private final boolean add;
	
	public UpdatePlayerCardGameClientCommand(Card card, boolean add) {
		this.card = card;
		this.add = add;
	}

	@Override
	public void execute(ClientUI ui, Connection connection) {
		CardRackGui rack = ui.<PanelGui>getPanel().getContent().getCardRackUI();
		if (add) {
			rack.onCardAdded(card);
		} else {
			rack.onCardRemoved(card);
		}
	}

}
