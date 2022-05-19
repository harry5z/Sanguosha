package listeners.game.server;

import java.util.Set;

import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.player.SyncHeroGameClientCommand;
import commands.game.client.sync.player.SyncResetWineEffectiveGameClientCommand;
import commands.game.client.sync.player.SyncWineUsedGameClientCommand;
import core.heroes.Hero;
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

}
