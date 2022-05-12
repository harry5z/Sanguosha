package core.server.game.controllers;

import cards.Card;
import core.player.PlayerCardZone;
import core.server.game.Game;

public interface CardSelectableGameController extends GameController {
	
	public void onCardSelected(Game game, Card card, PlayerCardZone zone);

}
