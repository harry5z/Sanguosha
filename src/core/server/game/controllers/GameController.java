package core.server.game.controllers;

import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public interface GameController {
	
	public void proceed(Game game) throws GameFlowInterruptedException;
	
}
