package commands.client.game.sync.player;

import cards.Card;
import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedType;

public class SyncDelayedAddedGameClientCommand extends AbstractSyncPlayerClientCommand {
	
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final Card card;
	private final DelayedType type;
	private final boolean add;
	
	public SyncDelayedAddedGameClientCommand(String name, Card card, DelayedType type, boolean add) {
		this.name = name;
		this.card = card;
		this.type = type;
		this.add = add;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(this.name)) {
			if (this.add) {
				state.getSelf().pushDelayed(this.card, this.type);
			} else {
				state.getSelf().removeDelayed(this.type);
			}
		} else {
			if (this.add) {
				state.getPlayer(this.name).pushDelayed(this.card, this.type);
			} else {
				state.getPlayer(this.name).removeDelayed(this.type);
			}
		}
	}

}
