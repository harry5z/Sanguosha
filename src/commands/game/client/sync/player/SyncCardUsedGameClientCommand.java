package commands.game.client.sync.player;

import cards.Card;
import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncCardUsedGameClientCommand extends AbstractSyncPlayerClientCommand {
	
	private static final long serialVersionUID = -3052922194486998505L;

	private final String name;
	private final Card card;
	
	public SyncCardUsedGameClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().getDisposalListener().onCardUsed(card);
		} else {
			state.getPlayer(name).getDisposalListener().onCardUsed(card);
		}
	}

}
