package core.server.game.controllers.interfaces;

import core.server.game.controllers.GameController;

public interface DodgeUsableGameController extends GameController {

	public void onDodged();
	
	public void onNotDodged();
	
}
