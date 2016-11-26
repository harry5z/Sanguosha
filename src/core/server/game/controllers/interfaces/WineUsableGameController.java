package core.server.game.controllers.interfaces;

import cards.Card;
import core.server.game.controllers.GameController;

public interface WineUsableGameController extends GameController {
	
	public void onWineUsed(Card wine);

}
