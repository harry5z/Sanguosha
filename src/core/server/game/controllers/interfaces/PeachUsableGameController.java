package core.server.game.controllers.interfaces;

import cards.Card;
import core.server.game.controllers.GameController;

public interface PeachUsableGameController extends GameController {
	
	public void onPeachUsed(Card peach);

}
