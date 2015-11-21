package listeners.game.server;

import java.util.Set;
import java.util.stream.Collectors;

import commands.game.client.sync.SyncAttackLimitsSetGameUIClientCommand;
import commands.game.client.sync.SyncAttackUsedGameUIClientCommand;
import commands.game.client.sync.SyncAttackUsedSetGameUIClientCommand;
import commands.game.client.sync.SyncFlipGameUIClientCommand;
import commands.game.client.sync.SyncWineUsedGameUIClientCommand;
import commands.game.client.sync.SyncWineUsedsSetGameUIClientCommand;
import listeners.game.PlayerStatusListener;
import net.server.GameRoom;

public class ServerInGamePlayerStatusListener extends ServerInGamePlayerListener implements PlayerStatusListener {

	public ServerInGamePlayerStatusListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}

	@Override
	public void onWineUsed() {
		final String name = this.name;
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> new SyncWineUsedGameUIClientCommand(name)
				)
			)
		);
	}

	@Override
	public void onAttackUsed() {
		room.sendCommandToPlayer(name, new SyncAttackUsedGameUIClientCommand());
	}

	@Override
	public void onSetAttackLimit(int limit) {
		room.sendCommandToPlayer(name, new SyncAttackLimitsSetGameUIClientCommand(limit));
	}

	@Override
	public void onSetAttackUsed(int amount) {
		room.sendCommandToPlayer(name, new SyncAttackUsedSetGameUIClientCommand(amount));
	}

	@Override
	public void onSetWineUsed(int amount) {
		room.sendCommandToPlayer(name, new SyncWineUsedsSetGameUIClientCommand(amount));
	}

	@Override
	public void onFlip(boolean flipped) {
		final String name = this.name;
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> new SyncFlipGameUIClientCommand(name, flipped)
				)
			)
		);
	}

}
