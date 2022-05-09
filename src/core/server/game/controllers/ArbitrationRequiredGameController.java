package core.server.game.controllers;

import cards.Card;

public interface ArbitrationRequiredGameController extends GameController {
	
	public void onArbitrationCompleted(Card card);

}
