package listeners.game.server;

import java.util.Set;

import cards.Card;
import commands.game.client.sync.delayed.SyncDelayedAddedGameUIClientCommand;
import core.server.GameRoom;
import listeners.game.DelayedListener;
import utils.DelayedType;

public class ServerInGameDelayedListener extends ServerInGamePlayerListener implements DelayedListener {

	public ServerInGameDelayedListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}

	@Override
	public void onDelayedAdded(Card card, DelayedType type) {
		this.room.sendCommandToAllPlayers(new SyncDelayedAddedGameUIClientCommand(this.name, card, type, true));
	}

	@Override
	public void onDelayedRemove(DelayedType type) {
		this.room.sendCommandToAllPlayers(new SyncDelayedAddedGameUIClientCommand(this.name, null, type, false));
	}

}
