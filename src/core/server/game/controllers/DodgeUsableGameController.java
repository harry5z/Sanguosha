package core.server.game.controllers;

public interface DodgeUsableGameController extends GameController {

	public void onDodged();
	
	public void onNotDodged();
	
}
