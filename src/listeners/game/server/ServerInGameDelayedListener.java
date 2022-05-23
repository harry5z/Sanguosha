package listeners.game.server;

import java.util.Set;

import cards.Card;
import commands.game.client.sync.player.SyncDelayedAddedGameClientCommand;
import core.player.PlayerCompleteServer;
import core.player.PlayerSimple;
import core.server.SyncController;
import listeners.game.DelayedListener;
import utils.DelayedStackItem;
import utils.DelayedType;

public class ServerInGameDelayedListener extends ServerInGamePlayerListener implements DelayedListener {

	public ServerInGameDelayedListener(String name, Set<String> allNames, SyncController controller) {
		super(name, allNames, controller);
	}

	@Override
	public void onDelayedAdded(Card card, DelayedType type) {
		this.controller.sendSyncCommandToAllPlayers(new SyncDelayedAddedGameClientCommand(this.name, card, type, true));
	}

	@Override
	public void onDelayedRemove(DelayedType type) {
		this.controller.sendSyncCommandToAllPlayers(new SyncDelayedAddedGameClientCommand(this.name, null, type, false));
	}

	@Override
	public void refreshSelf(PlayerCompleteServer self) {
		for (DelayedStackItem item : self.getDelayedQueue()) {
			controller.sendSyncCommandToPlayer(name, new SyncDelayedAddedGameClientCommand(self.getName(), item.delayed, item.type, true));
		}
	}

	@Override
	public void refreshOther(PlayerSimple other) {
		for (DelayedStackItem item : other.getDelayedQueue()) {
			controller.sendSyncCommandToPlayer(name, new SyncDelayedAddedGameClientCommand(other.getName(), item.delayed, item.type, true));
		}
	}

}
