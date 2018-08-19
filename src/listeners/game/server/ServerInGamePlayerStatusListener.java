package listeners.game.server;

import java.util.Set;

import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.status.SyncAttackLimitsSetGameUIClientCommand;
import commands.game.client.sync.status.SyncAttackUsedGameUIClientCommand;
import commands.game.client.sync.status.SyncAttackUsedSetGameUIClientCommand;
import commands.game.client.sync.status.SyncChainGameUIClientCommand;
import commands.game.client.sync.status.SyncFlipGameUIClientCommand;
import commands.game.client.sync.status.SyncResetWineEffectiveGameUIClientCommand;
import commands.game.client.sync.status.SyncWineUsedGameUIClientCommand;
import commands.game.client.sync.status.SyncWineUsedsSetGameUIClientCommand;
import core.server.GameRoom;
import listeners.game.PlayerStatusListener;

public class ServerInGamePlayerStatusListener extends ServerInGamePlayerListener implements PlayerStatusListener {

	public ServerInGamePlayerStatusListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}

	@Override
	public void onWineUsed() {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncWineUsedGameUIClientCommand(name)
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
	public void onResetWineEffective() {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncResetWineEffectiveGameUIClientCommand(name)
			)
		);
	}

	@Override
	public void onFlip(boolean flipped) {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncFlipGameUIClientCommand(name, flipped)
			)
		);
	}
	
	@Override
	public void onChained(boolean chained) {
		room.sendCommandToAllPlayers(new SyncChainGameUIClientCommand(this.name, chained));
	}

}
