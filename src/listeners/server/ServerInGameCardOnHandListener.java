package listeners.server;

import java.util.Set;
import java.util.stream.Collectors;

import net.server.GameRoom;
import cards.Card;
import commands.game.client.UpdateOtherPlayerCardGameClientCommand;
import commands.game.client.UpdatePlayerCardGameClientCommand;
import listeners.game.CardOnHandListener;

public class ServerInGameCardOnHandListener implements CardOnHandListener {

	private final String name;
	private final Set<String> otherNames;
	private final GameRoom room;
	
	public ServerInGameCardOnHandListener(String name, Set<String> allNames, GameRoom room) {
		this.name = name;
		this.room = room;
		this.otherNames = allNames.stream().filter(n -> !n.equals(name)).collect(Collectors.toSet());
	}
	@Override
	public void onCardAdded(Card card) {
		room.sendCommandToPlayer(name, new UpdatePlayerCardGameClientCommand(card, true));
		room.sendCommandToPlayers(otherNames.stream().collect(Collectors.toMap(n -> n, n -> new UpdateOtherPlayerCardGameClientCommand(name, true, 1))));
	}

	@Override
	public void onCardRemoved(Card card) {
		room.sendCommandToPlayer(name, new UpdatePlayerCardGameClientCommand(card, false));
		room.sendCommandToPlayers(otherNames.stream().collect(Collectors.toMap(n -> n, n -> new UpdateOtherPlayerCardGameClientCommand(name, false, 1))));
	}

}
