package commands.game.client.sync.disposal;

import cards.Card;
import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;

public class SyncCardUsedGameUIClientCommand extends AbstractGameUIClientCommand {
	
	private static final long serialVersionUID = -3052922194486998505L;

	private final String name;
	private final Card card;
	
	public SyncCardUsedGameUIClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void execute(GamePanel panel) {
		if (panel.getContent().getSelf().getName().equals(name)) {
			panel.getContent().getSelf().getDisposalListener().onCardUsed(card);
		} else {
			panel.getContent().getPlayer(name).getDisposalListener().onCardUsed(card);
		}
	}

}
