package listeners.game.server;

import java.util.Set;

import cards.Card;
import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.disposal.SyncCardDisposedGameUIClientCommand;
import commands.game.client.sync.disposal.SyncCardUsedGameUIClientCommand;
import commands.game.client.sync.disposal.SyncDisposalAreaRefreshGameUIClientCommand;
import core.Deck;
import core.server.GameRoom;
import listeners.game.CardDisposalListener;

public class ServerInGameCardDisposalListener extends ServerInGamePlayerListener implements CardDisposalListener {
	
	private final Deck deck;
	
	public ServerInGameCardDisposalListener(String name, Set<String> playerNames, GameRoom room) {
		super(name, playerNames, room);
		this.deck = room.getGame().getDeck();
	}

	@Override
	public void onCardUsed(Card card) {
		this.deck.discard(card);
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncCardUsedGameUIClientCommand(name, card)
			)
		);
	}

	@Override
	public void onCardDisposed(Card card) {
		// TODO: some abilities might affect disposed cards
		this.deck.discard(card);
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncCardDisposedGameUIClientCommand(name, card)
			)
		);
	}

	@Override
	public void refresh() {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncDisposalAreaRefreshGameUIClientCommand()
			)
		);
	}

}
