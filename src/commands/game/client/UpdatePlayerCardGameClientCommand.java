package commands.game.client;

import cards.Card;
import net.Connection;
import net.client.ClientUI;
import ui.game.CardRackGui;
import ui.game.GamePanelUI;

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
		CardRackGui rack = ui.<GamePanelUI>getPanel().getContent().getCardRackUI();
		if (add) {
			rack.onCardAdded(card);
		} else {
			rack.onCardRemoved(card);
		}
	}

}
