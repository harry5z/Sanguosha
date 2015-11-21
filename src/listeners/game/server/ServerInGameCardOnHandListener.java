package listeners.game.server;

import java.util.Set;
import java.util.stream.Collectors;

import listeners.game.CardOnHandListener;
import net.server.GameRoom;
import cards.Card;
import commands.game.client.sync.SyncOtherPlayerCardGameUIClientCommand;
import commands.game.client.sync.SyncPlayerCardGameClientCommand;

public class ServerInGameCardOnHandListener extends ServerInGamePlayerListener implements CardOnHandListener {

	public ServerInGameCardOnHandListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}
	@Override
	public void onCardAdded(Card card) {
		room.sendCommandToPlayer(name, new SyncPlayerCardGameClientCommand(card, true));
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> new SyncOtherPlayerCardGameUIClientCommand(name, true, 1)
				)
			)
		);
	}

	@Override
	public void onCardRemoved(Card card) {
		// server side calling disposal results in double removal of a card, this is hacky
//		room.sendCommandToPlayer(name, new UpdatePlayerCardGameClientCommand(card, false));
//		room.sendCommandToPlayers(otherNames.stream().collect(Collectors.toMap(n -> n, n -> new UpdateOtherPlayerCardGameClientCommand(name, false, 1))));
	}

}
