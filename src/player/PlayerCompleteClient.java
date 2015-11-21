package player;

import listeners.game.GameListener;

public class PlayerCompleteClient extends PlayerComplete {

	private GameListener gameListener;

	public PlayerCompleteClient(String name, int position) {
		super(name, position);
	}

	public void registerGameListener(GameListener listener) {
		gameListener = listener;
	}

	public GameListener getGameListener() {
		return gameListener;
	}

}
