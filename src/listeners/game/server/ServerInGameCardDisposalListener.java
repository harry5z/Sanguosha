package listeners.game.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import commands.game.client.GameClientCommand;
import core.Deck;
import exceptions.server.game.InvalidPlayerCommandException;
import listeners.game.CardDisposalListener;
import net.server.GameRoom;
import ui.game.GamePanelUI;

public class ServerInGameCardDisposalListener extends ServerInGamePlayerListener implements CardDisposalListener {
	
	private final List<Card> cards;
	private final Deck deck;

	public ServerInGameCardDisposalListener(String name, Set<String> playerNames, GameRoom room) {
		super(name, playerNames, room);
		this.cards = new ArrayList<>();
		this.deck = room.getGame().getDeck();
	}

	@Override
	public void onCardUsed(Card card) {
		this.cards.add(card);
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> {
				try {
					ui.<GamePanelUI>getPanel().getContent().getSelf().useCard(card);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> {
						try {
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).useCard(card);
						} catch (InvalidPlayerCommandException e) {
							e.printStackTrace();
						}
					})
				)
			)
		);
	}

	@Override
	public void onCardDisposed(Card card) {
		this.cards.add(card);
		room.sendCommandToPlayer(
			name, 
			(ui, connection) -> {
				try {
					ui.<GamePanelUI>getPanel().getContent().getSelf().discardCard(card);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
			}
		);
		final String playerName = name; // To avoid referencing "this" while serializing
		room.sendCommandToPlayers(
			otherNames.stream().collect(
				Collectors.toMap(
					n -> n, 
					n -> ((ui, connection) -> {
						try {
							ui.<GamePanelUI>getPanel().getContent().getPlayer(playerName).discardCard(card);
						} catch (InvalidPlayerCommandException e) {
							e.printStackTrace();
						}
					})
				)
			)
		);		
	}

	@Override
	public void refresh() {
		if (cards.size() > 0) {
			deck.discardAll(cards);
			cards.clear();
			GameClientCommand command = (ui, connection) -> ui.<GamePanelUI>getPanel().getContent().getSelf().clearDisposalArea();
			Map<String, GameClientCommand> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> command));
			map.put(name, command);
			room.sendCommandToPlayers(map);
		}
	}

}
