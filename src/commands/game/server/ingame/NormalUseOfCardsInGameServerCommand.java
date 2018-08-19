package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerInfo;
import core.server.game.Game;
import exceptions.server.game.InvalidPlayerCommandException;

public class NormalUseOfCardsInGameServerCommand extends UseOfCardsInGameServerCommand {

	private static final long serialVersionUID = 1522576291230124754L;

	public NormalUseOfCardsInGameServerCommand(PlayerInfo player, Set<Card> cards) {
		super(player, cards);
	}

	@Override
	public void execute(Game game) {
		try {
			game.findPlayer(this.getSource()).useCards(this.getCardsUsed());
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
			return;
		}
	}

}