package commands.game.client;

import java.util.Collections;

import net.client.GamePanel;
import player.PlayerSimple;

public class UpdateOtherPlayerCardGameUIClientCommand extends GameUIClientCommand {

	private static final long serialVersionUID = 7418973417086495080L;
	
	private final String name;
	private final boolean add;
	private final int amount;
	
	public UpdateOtherPlayerCardGameUIClientCommand(String name, boolean add, int amount) {
		this.name = name;
		this.add = add;
		this.amount = amount;
	}

	@Override
	public void execute(GamePanel panel) {
		PlayerSimple player = panel.getContent().getPlayer(name);
		if (add) {
			player.addCards(Collections.nCopies(amount, null));
		} else {
			for (int i = 0; i < amount; i++) {
				player.removeCardFromHand(null);
			}
		}
	}

}
