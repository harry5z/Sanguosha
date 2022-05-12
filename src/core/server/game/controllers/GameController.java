package core.server.game.controllers;

import core.server.game.GameInternal;
import exceptions.server.game.GameFlowInterruptedException;

public interface GameController {
	
	public void proceed(GameInternal game) throws GameFlowInterruptedException;
	
}
