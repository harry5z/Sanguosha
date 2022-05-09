package core.server.game.controllers;

import cards.Card;
import core.player.PlayerCardZone;

public interface CardSelectableGameController extends GameController {
	
	public void onCardSelected(Card card, PlayerCardZone zone);

}
