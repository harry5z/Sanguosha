package commands.game.client.sync.cardonhand;

import cards.Card;
import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import core.player.PlayerCompleteClient;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncPlayerCardGameUIClientCommand extends AbstractGameUIClientCommand {
	private static final long serialVersionUID = 5370641268667157302L;

	private final Card card;
	private final boolean add;
	
	public SyncPlayerCardGameUIClientCommand(Card card, boolean add) {
		this.card = card;
		this.add = add;
	}

	@Override
	public void execute(GamePanel<? extends Hero> panel) {
		PlayerCompleteClient player = panel.getContent().getSelf();
		if (add) {
			player.addCard(card);
		} else {
			try {
				player.removeCardFromHand(card);
			} catch (InvalidPlayerCommandException e) {
				e.printStackTrace();
			}
		}
	}

}
