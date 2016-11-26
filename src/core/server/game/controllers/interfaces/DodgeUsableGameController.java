package core.server.game.controllers.interfaces;

import cards.Card;
import core.server.game.controllers.GameController;

public interface DodgeUsableGameController extends GameController {

	public void onDodgeUsed(Card card);
	
	public void onDodgeNotUsed();
	
}
