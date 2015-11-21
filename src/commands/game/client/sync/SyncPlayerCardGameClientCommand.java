package commands.game.client.sync;

import cards.Card;
import commands.game.client.GameClientCommand;
import exceptions.server.game.InvalidPlayerCommandException;
import net.Connection;
import net.client.ClientUI;
import player.PlayerComplete;
import ui.game.GamePanelUI;

public class SyncPlayerCardGameClientCommand implements GameClientCommand {
	private static final long serialVersionUID = 5370641268667157302L;

	private final Card card;
	private final boolean add;
	
	public SyncPlayerCardGameClientCommand(Card card, boolean add) {
		this.card = card;
		this.add = add;
	}

	@Override
	public void execute(ClientUI ui, Connection connection) {
		synchronized (ui) {
			PlayerComplete player = ui.<GamePanelUI>getPanel().getContent().getSelf();
			if (add) {
				player.addCard(card);
			} else {
				try {
					player.removeCardFromHand(card);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
