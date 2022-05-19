package commands.game.client.sync.player;

import cards.Card;
import core.GameState;
import core.player.PlayerCompleteClient;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncPlayerCardGameClientCommand extends AbstractSyncPlayerClientCommand {
	private static final long serialVersionUID = 5370641268667157302L;

	private final Card card;
	private final boolean add;
	
	public SyncPlayerCardGameClientCommand(Card card, boolean add) {
		this.card = card;
		this.add = add;
	}

	@Override
	public void sync(GameState state) throws InvalidPlayerCommandException {
		PlayerCompleteClient player = state.getSelf();
		if (add) {
			player.addCard(card);
		} else {
			player.removeCardFromHand(card);
		}
	}

}
