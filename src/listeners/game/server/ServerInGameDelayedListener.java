package listeners.game.server;

import java.util.Set;

import cards.Card;
import commands.game.client.sync.delayed.SyncDelayedAddedGameUIClientCommand;
import core.server.SyncController;
import listeners.game.DelayedListener;
import utils.DelayedType;

public class ServerInGameDelayedListener extends ServerInGamePlayerListener implements DelayedListener {

	public ServerInGameDelayedListener(String name, Set<String> allNames, SyncController controller) {
		super(name, allNames, controller);
	}

	@Override
	public void onDelayedAdded(Card card, DelayedType type) {
		this.controller.sendSyncCommandToAllPlayers(new SyncDelayedAddedGameUIClientCommand(this.name, card, type, true));
	}

	@Override
	public void onDelayedRemove(DelayedType type) {
		this.controller.sendSyncCommandToAllPlayers(new SyncDelayedAddedGameUIClientCommand(this.name, null, type, false));
	}

}
