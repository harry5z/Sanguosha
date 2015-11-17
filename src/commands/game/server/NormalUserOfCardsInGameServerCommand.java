package commands.game.server;

import java.util.Set;

import cards.Card;
import core.Game;
import core.PlayerInfo;

public class NormalUserOfCardsInGameServerCommand extends UseOfCardsInGameServerCommand {

	private static final long serialVersionUID = 1522576291230124754L;

	public NormalUserOfCardsInGameServerCommand(PlayerInfo player, Set<Card> cards) {
		super(player, cards);
	}

	@Override
	public void execute(Game game) {
		game.findPlayer(getSource()).useCards(getCardsUsed());
	}

}
