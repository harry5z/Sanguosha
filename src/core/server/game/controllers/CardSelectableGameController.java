package core.server.game.controllers;

import cards.Card;
import core.player.PlayerCardZone;
import core.server.game.GameInternal;
import exceptions.server.game.IllegalPlayerActionException;

public interface CardSelectableGameController extends GameController {
	
	public void onCardSelected(GameInternal game, Card card, PlayerCardZone zone);
	
	/**
	 * Validate whether a certain card can be selected. If card can be validated, it is validated, or it is left untouched
	 * 
	 * @param game
	 * @param card
	 * @param zone
	 * @throws IllegalPlayerActionException
	 */
	public void validateCardSelected(GameInternal game, Card card, PlayerCardZone zone) throws IllegalPlayerActionException;

}
