package listeners.game.client;

import listeners.game.PlayerStatusListener;
import ui.game.GamePanelUI;

public class ClientPlayerStatusListener implements PlayerStatusListener {
	
	private final GamePanelUI ui;
	
	public ClientPlayerStatusListener(GamePanelUI ui) {
		this.ui = ui;
	}

	@Override
	public void onWineUsed() {
		// TODO ui
	}

	@Override
	public void onAttackUsed() {
		
	}

	@Override
	public void onSetAttackLimit(int limit) {

	}

	@Override
	public void onSetAttackUsed(int amount) {

	}

	@Override
	public void onSetWineUsed(int amount) {

	}

	@Override
	public void onFlip(boolean flipped) {
		// TODO ui
	}

}
