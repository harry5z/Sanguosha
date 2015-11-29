package core.server.game.controllers;

import cards.Card;

public interface WineUsableGameController extends GameController {
	
	public void onWineUsed(Card wine);

}
