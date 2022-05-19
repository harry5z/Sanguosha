package commands.game.client.sync.player;

import cards.Card;
import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncCardDisposedGameClientCommand extends AbstractSyncPlayerClientCommand {
	
	private static final long serialVersionUID = 8495650738265955748L;

	private final String name;
	private final Card card;
	
	public SyncCardDisposedGameClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().getDisposalListener().onCardDisposed(card);
		} else {
			state.getPlayer(name).getDisposalListener().onCardDisposed(card);
		}
	}

}
