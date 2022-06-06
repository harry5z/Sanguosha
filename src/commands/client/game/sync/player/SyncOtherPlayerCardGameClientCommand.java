package commands.client.game.sync.player;

import java.util.Collections;

import core.GameState;
import core.player.PlayerSimple;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncOtherPlayerCardGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 7418973417086495080L;
	
	private final String name;
	private final boolean add;
	private final int amount;
	
	public SyncOtherPlayerCardGameClientCommand(String name, boolean add, int amount) {
		this.name = name;
		this.add = add;
		this.amount = amount;
	}

	@Override
	public void sync(GameState state) throws InvalidPlayerCommandException {
		PlayerSimple player = state.getPlayer(name);
		if (add) {
			player.addCards(Collections.nCopies(amount, null));
		} else {
			for (int i = 0; i < amount; i++) {
				player.removeCardFromHand(null);
			}
		}
	}

}
