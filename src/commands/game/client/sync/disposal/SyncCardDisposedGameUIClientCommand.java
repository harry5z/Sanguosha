package commands.game.client.sync.disposal;

import cards.Card;
import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;

public class SyncCardDisposedGameUIClientCommand extends AbstractSyncGameUIClientCommand {
	
	private static final long serialVersionUID = 8495650738265955748L;

	private final String name;
	private final Card card;
	
	public SyncCardDisposedGameUIClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void sync(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(name)) {
			panel.getGameState().getSelf().getDisposalListener().onCardDisposed(card);
		} else {
			panel.getGameState().getPlayer(name).getDisposalListener().onCardDisposed(card);
		}
	}

}
