package core.server.game.controllers;

import exceptions.server.game.GameFlowInterruptedException;

public interface GameController {
	
	public void proceed() throws GameFlowInterruptedException;
	
}
