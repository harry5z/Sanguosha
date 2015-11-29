package core.server.game.controllers;

import cards.Card;

public interface DodgeUsableGameController extends GameController {

	public void onDodgeUsed(Card card);
	
	public void onDodgeNotUsed();
	
}
