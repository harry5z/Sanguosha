package core.server.game.controllers;

import cards.Card;

public interface AttackUsableGameController extends GameController {

	public void onAttackUsed(Card card);
	
	public void onAttackNotUsed();
}
