package commands.client.game.sync.player;

import cards.Card;
import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncCardShownGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final Card card;
	
	public SyncCardShownGameClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().getDisposalListener().onCardShown(card);
		} else {
			state.getPlayer(name).getDisposalListener().onCardShown(card);
		}
	}
}
