package commands.game.client.sync.disposal;

import cards.Card;
import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;

public class SyncCardShownGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final Card card;
	
	public SyncCardShownGameUIClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void execute(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(name)) {
			panel.getGameState().getSelf().getDisposalListener().onCardShown(card);
		} else {
			panel.getGameState().getPlayer(name).getDisposalListener().onCardShown(card);
		}
	}
}
