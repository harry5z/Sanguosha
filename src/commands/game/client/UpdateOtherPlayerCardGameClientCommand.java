package commands.game.client;

import java.util.Collections;

import net.Connection;
import net.client.ClientUI;
import player.PlayerSimple;
import ui.game.GamePanelUI;

public class UpdateOtherPlayerCardGameClientCommand implements GameClientCommand {

	private static final long serialVersionUID = 7418973417086495080L;
	
	private final String name;
	private final boolean add;
	private final int amount;
	
	public UpdateOtherPlayerCardGameClientCommand(String name, boolean add, int amount) {
		this.name = name;
		this.add = add;
		this.amount = amount;
	}

	@Override
	public void execute(ClientUI ui, Connection connection) {
		PlayerSimple player = ui.<GamePanelUI>getPanel().getContent().getPlayer(name);
		if (add) {
			player.addCards(Collections.nCopies(amount, null));
		} else {
			for (int i = 0; i < amount; i++) {
				player.removeCardFromHand(null);
			}
		}
	}

}
