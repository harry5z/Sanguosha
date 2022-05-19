package listeners.game.server;

import java.util.Set;

import cards.Card;
import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.player.SyncOtherPlayerCardGameClientCommand;
import commands.game.client.sync.player.SyncPlayerCardGameClientCommand;
import core.server.SyncController;
import listeners.game.CardOnHandListener;

public class ServerInGameCardOnHandListener extends ServerInGamePlayerListener implements CardOnHandListener {

	public ServerInGameCardOnHandListener(String name, Set<String> allNames, SyncController controller) {
		super(name, allNames, controller);
	}
	@Override
	public void onCardAdded(Card card) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForDifferentCommand(
				name, 
				otherNames, 
				new SyncPlayerCardGameClientCommand(card, true), 
				new SyncOtherPlayerCardGameClientCommand(name, true, 1)
			)
		);
	}

	@Override
	public void onCardRemoved(Card card) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForDifferentCommand(
				name, 
				otherNames, 
				new SyncPlayerCardGameClientCommand(card, false), 
				new SyncOtherPlayerCardGameClientCommand(name, false, 1)
			)
		);	
	}

}
