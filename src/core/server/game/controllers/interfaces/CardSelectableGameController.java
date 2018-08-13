package core.server.game.controllers.interfaces;

import cards.Card;
import core.player.PlayerCardZone;
import core.server.game.controllers.GameController;

public interface CardSelectableGameController extends GameController {
	
	public void onCardSelected(Card card, PlayerCardZone zone);

}
