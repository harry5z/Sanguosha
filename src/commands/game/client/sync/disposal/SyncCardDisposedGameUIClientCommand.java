package commands.game.client.sync.disposal;

import cards.Card;
import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;

public class SyncCardDisposedGameUIClientCommand extends AbstractGameUIClientCommand {
	
	private static final long serialVersionUID = 8495650738265955748L;

	private final String name;
	private final Card card;
	
	public SyncCardDisposedGameUIClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getName().equals(name)) {
			panel.getContent().getSelf().getDisposalListener().onCardDisposed(card);
		} else {
			panel.getContent().getPlayer(name).getDisposalListener().onCardDisposed(card);
		}
	}

}
