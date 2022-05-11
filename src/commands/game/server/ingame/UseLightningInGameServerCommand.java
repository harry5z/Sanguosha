package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedType;

public class UseLightningInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 9088626402130251064L;
	
	private final Card card;

	public UseLightningInGameServerCommand(Card card) {
		this.card = card;
	}

	@Override
	public void execute(Game game) {
		PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
		try {
			currentPlayer.removeCardFromHand(card);
			currentPlayer.pushDelayed(card, DelayedType.LIGHTNING);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
