package listeners.game.server;

import java.util.Set;

import cards.Card;
import commands.client.game.sync.SyncCommandsUtil;
import commands.client.game.sync.player.SyncCardDisposedGameClientCommand;
import commands.client.game.sync.player.SyncCardShownGameClientCommand;
import commands.client.game.sync.player.SyncCardUsedGameClientCommand;
import commands.client.game.sync.player.SyncDisposalAreaRefreshGameClientCommand;
import core.Deck;
import core.player.PlayerCompleteServer;
import core.player.PlayerSimple;
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
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncCardUsedGameClientCommand(name, card)
			)
		);
	}

	@Override
	public void onCardDisposed(Card card) {
		this.deck.discard(card);
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncCardDisposedGameClientCommand(name, card)
			)
		);
	}
	
	@Override
	public void onCardShown(Card card) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncCardShownGameClientCommand(name, card)
			)
		);
	}

	@Override
	public void refresh() {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncDisposalAreaRefreshGameClientCommand()
			)
		);
	}

	@Override
	public void refreshSelf(PlayerCompleteServer self) {
		// nothing to refresh
	}

	@Override
	public void refreshOther(PlayerSimple other) {
		// nothing to refresh
	}

}
