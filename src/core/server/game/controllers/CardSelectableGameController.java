package core.server.game.controllers;

import cards.Card;
import core.player.PlayerCardZone;
import core.server.game.GameInternal;

public interface CardSelectableGameController extends GameController {
	
	public void onCardSelected(GameInternal game, Card card, PlayerCardZone zone);

}
