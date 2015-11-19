package listeners.server;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import commands.game.client.GameClientCommand;
import listeners.game.CardDisposalListener;
import net.server.GameRoom;
import ui.game.GamePanelUI;

public class ServerInGameCardDisposalListener extends ServerInGamePlayerListener implements CardDisposalListener {

	public ServerInGameCardDisposalListener(String name, Set<String> playerNames, GameRoom room) {
		super(name, playerNames, room);
	}

	@Override
	public void onCardUsed(Card card) {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getSelf().useCard(card)
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).useCard(card))
				)
			)
		);
	}

	@Override
	public void onCardDisposed(Card card) {
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getSelf().discardCard(card)
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).discardCard(card))
				)
			)
		);		
	}

	@Override
	public void refresh() {
		GameClientCommand command = (ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getSelf().clearDisposalArea();
		Map<String, GameClientCommand> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> command));
		map.put(name, command);
		room.sendCommandToPlayers(map);
	}

}
