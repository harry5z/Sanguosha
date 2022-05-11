package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedType;

public class InitiateStarvationInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private final Card card;

	public InitiateStarvationInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
		this.card = card;
	}

	@Override
	public void execute(Game game) {
		PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
		try {
			currentPlayer.removeCardFromHand(card);
			game.findPlayer(this.target).pushDelayed(card, DelayedType.STARVATION);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}
}
