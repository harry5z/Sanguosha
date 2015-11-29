package core.server.game.controllers;

import cards.Card;

public interface PeachUsableGameController extends GameController {
	
	public void onPeachUsed(Card peach);

}
