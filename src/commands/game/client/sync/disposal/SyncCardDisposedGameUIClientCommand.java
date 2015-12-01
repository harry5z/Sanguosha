package commands.game.client.sync.disposal;

import cards.Card;
import commands.game.client.GeneralGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncCardDisposedGameUIClientCommand extends GeneralGameUIClientCommand {
	
	private static final long serialVersionUID = 8495650738265955748L;

	private final String name;
	private final Card card;
	
	public SyncCardDisposedGameUIClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		try {
			if (panel.getContent().getSelf().getName().equals(name)) {
				panel.getContent().getSelf().discardCard(card);
			} else {
				panel.getContent().getPlayer(name).discardCard(card);
			}
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
