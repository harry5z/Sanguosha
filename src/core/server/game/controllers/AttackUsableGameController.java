package core.server.game.controllers;

import cards.Card;
import core.server.game.GameInternal;

public interface AttackUsableGameController extends GameController {

	public void onAttackUsed(GameInternal game, Card card);
	
	public void onAttackNotUsed(GameInternal game);
}
