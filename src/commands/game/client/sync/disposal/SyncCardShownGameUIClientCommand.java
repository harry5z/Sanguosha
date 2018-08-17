package commands.game.client.sync.disposal;

import cards.Card;
import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;

public class SyncCardShownGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final Card card;
	
	public SyncCardShownGameUIClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getName().equals(name)) {
			panel.getContent().getSelf().getDisposalListener().onCardShown(card);
		} else {
			panel.getContent().getPlayer(name).getDisposalListener().onCardShown(card);
		}
	}
}
