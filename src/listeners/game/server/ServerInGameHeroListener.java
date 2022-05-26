package listeners.game.server;

import java.util.Set;

import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.player.SyncChainGameClientCommand;
import commands.game.client.sync.player.SyncFlipGameClientCommand;
import commands.game.client.sync.player.SyncHeroGameClientCommand;
import commands.game.client.sync.player.SyncResetWineEffectiveGameClientCommand;
import commands.game.client.sync.player.SyncRoleGameClientCommand;
import commands.game.client.sync.player.SyncWineUsedGameClientCommand;
import core.heroes.Hero;
import core.player.PlayerCompleteServer;
import core.player.PlayerSimple;
import core.player.Role;
import core.server.SyncController;
import listeners.game.HeroListener;

public class ServerInGameHeroListener extends ServerInGamePlayerListener implements HeroListener {

	public ServerInGameHeroListener(String name, Set<String> allNames, SyncController controller) {
		super(name, allNames, controller);
	}

	@Override
	public void onHeroRegistered(Hero hero) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncHeroGameClientCommand(name, hero)
			)
		);
	}

	@Override
	public void onWineEffective(boolean effective) {
		if (effective) {
			controller.sendSyncCommandToPlayers(
				SyncCommandsUtil.generateMapForSameCommand(
					name, 
					otherNames, 
					new SyncWineUsedGameClientCommand(name)
				)
			);
		} else {
			controller.sendSyncCommandToPlayers(
				SyncCommandsUtil.generateMapForSameCommand(
					name, 
					otherNames, 
					new SyncResetWineEffectiveGameClientCommand(name)
				)
			);
		}
	}

	@Override
	public void onRoleAssigned(Role role) {
		if (role == Role.EMPEROR) {
			controller.sendSyncCommandToAllPlayers(new SyncRoleGameClientCommand(name, role));
		} else {
			controller.sendSyncCommandToPlayer(name, new SyncRoleGameClientCommand(name, role));
		}
	}
	
	@Override
	public void onRoleRevealed(Role role) {
		controller.sendSyncCommandToAllPlayers(new SyncRoleGameClientCommand(name, role));
	}
	
	@Override
	public void onFlipped(boolean flipped) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncFlipGameClientCommand(name, flipped)
			)
		);
	}
	
	@Override
	public void onChained(boolean chained) {
		controller.sendSyncCommandToAllPlayers(new SyncChainGameClientCommand(this.name, chained));
	}

	@Override
	public void refreshSelf(PlayerCompleteServer self) {
		controller.sendSyncCommandToPlayer(name, new SyncRoleGameClientCommand(self.getName(), self.getRole()));
		controller.sendSyncCommandToPlayer(name, new SyncHeroGameClientCommand(self.getName(), self.getHero()));
		controller.sendSyncCommandToPlayer(name, new SyncFlipGameClientCommand(self.getName(), self.isFlipped()));
		controller.sendSyncCommandToPlayer(name, new SyncChainGameClientCommand(self.getName(), self.isChained()));
		if (self.isWineEffective()) {
			controller.sendSyncCommandToPlayer(name, new SyncWineUsedGameClientCommand(self.getName()));
		}
	}

	@Override
	public void refreshOther(PlayerSimple other) {
		controller.sendSyncCommandToPlayer(name, new SyncHeroGameClientCommand(other.getName(), other.getHero()));
		controller.sendSyncCommandToPlayer(name, new SyncFlipGameClientCommand(other.getName(), other.isFlipped()));
		controller.sendSyncCommandToPlayer(name, new SyncChainGameClientCommand(other.getName(), other.isChained()));
		if (other.isWineEffective()) {
			controller.sendSyncCommandToPlayer(name, new SyncWineUsedGameClientCommand(other.getName()));
		}
		if (other.getRole() == Role.EMPEROR || !other.isAlive()) {
			controller.sendSyncCommandToPlayer(name, new SyncRoleGameClientCommand(other.getName(), other.getRole()));
		}
	}


}
