package listeners.game.server;

import java.util.Set;

import cards.Card;
import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.cardonhand.SyncOtherPlayerCardGameUIClientCommand;
import commands.game.client.sync.cardonhand.SyncPlayerCardGameUIClientCommand;
import core.server.GameRoom;
import listeners.game.CardOnHandListener;

public class ServerInGameCardOnHandListener extends ServerInGamePlayerListener implements CardOnHandListener {

	public ServerInGameCardOnHandListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}
	@Override
	public void onCardAdded(Card card) {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForDifferentCommand(
				name, 
				otherNames, 
				new SyncPlayerCardGameUIClientCommand(card, true), 
				new SyncOtherPlayerCardGameUIClientCommand(name, true, 1)
			)
		);
	}

	@Override
	public void onCardRemoved(Card card) {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForDifferentCommand(
				name, 
				otherNames, 
				new SyncPlayerCardGameUIClientCommand(card, false), 
				new SyncOtherPlayerCardGameUIClientCommand(name, false, 1)
			)
		);	
	}

}
