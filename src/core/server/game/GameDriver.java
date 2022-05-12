package core.server.game;

import core.server.ConnectionController;
import core.server.game.controllers.GameController;

public interface GameDriver {

	public void pushGameController(GameController controller);

	public ConnectionController getConnectionController();
	// TODO remove #getConnectionController from GameDriver
}
