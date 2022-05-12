package core.server.game.controllers;

import cards.Card;
import core.server.game.Game;

public interface AttackUsableGameController extends GameController {

	public void onAttackUsed(Game game, Card card);
	
	public void onAttackNotUsed(Game game);
}
