package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerCardZone;
import core.server.game.Game;
import core.server.game.controllers.interfaces.CardSelectableGameController;

public class PlayerCardSelectionInGameServerCommand extends InGameServerCommand {
	
	private static final long serialVersionUID = 1L;
	
	private final Card card;
	private final PlayerCardZone zone;
	
	public PlayerCardSelectionInGameServerCommand(Card card, PlayerCardZone zone) {
		this.card = card;
		this.zone = zone;
	}

	@Override
	public void execute(Game game) {
		game.<CardSelectableGameController>getGameController().onCardSelected(this.card, this.zone);
	}

}
