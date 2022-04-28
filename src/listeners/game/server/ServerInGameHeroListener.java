package listeners.game.server;

import java.util.Set;

import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.hero.SyncHeroGameUIClientCommand;
import core.heroes.Hero;
import core.server.GameRoom;
import listeners.game.HeroListener;

public class ServerInGameHeroListener extends ServerInGamePlayerListener implements HeroListener {

	public ServerInGameHeroListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}

	@Override
	public void onHeroRegistered(Hero hero) {
		room.sendCommandToPlayers(
				SyncCommandsUtil.generateMapForSameCommand(
					name, 
					otherNames, 
					new SyncHeroGameUIClientCommand(name, hero)
				)
			);
	}

}
