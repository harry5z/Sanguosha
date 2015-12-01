package commands.game.client.sync.disposal;

import cards.Card;
import commands.game.client.GeneralGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncCardUsedGameUIClientCommand extends GeneralGameUIClientCommand {
	
	private static final long serialVersionUID = -3052922194486998505L;

	private final String name;
	private final Card card;
	
	public SyncCardUsedGameUIClientCommand(String name, Card card) {
		this.name = name;
		this.card = card;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		try {
			if (panel.getContent().getSelf().getName().equals(name)) {
				panel.getContent().getSelf().useCard(card);
			} else {
				panel.getContent().getPlayer(name).useCard(card);
			}
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
