package listeners.game.client;

import core.heroes.original.HeroOriginal;
import listeners.game.PlayerStatusListener;
import ui.game.interfaces.ClientGameUI;

public class ClientPlayerStatusListener implements PlayerStatusListener {
	
	private final ClientGameUI<HeroOriginal> ui;
	
	public ClientPlayerStatusListener(ClientGameUI<HeroOriginal> ui) {
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

	@Override
	public void onResetWineEffective() {
		// TODO ui
	}

}
