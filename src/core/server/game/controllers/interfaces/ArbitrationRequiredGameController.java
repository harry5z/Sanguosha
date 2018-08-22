package core.server.game.controllers.interfaces;

import cards.Card;
import core.server.game.controllers.GameController;

public interface ArbitrationRequiredGameController extends GameController {
	
	public void onArbitrationCompleted(Card card);

}
