package core.server.game.controllers.specials;

import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;

public abstract class AbstractSpecialGameController<T extends GameControllerStage<?>> extends AbstractGameController<T> implements SpecialGameController {
	
	protected boolean neutralized;
	protected int neutralizedCount;

	public AbstractSpecialGameController() {
		this.neutralized = false;
		this.neutralizedCount = 0;
	}

	@Override
	public void onNeutralized() {
		this.neutralized = !this.neutralized;
		this.neutralizedCount = 0;
	}
	
	@Override
	public void onNeutralizationCanceled() {
		this.neutralizedCount++;
	}
	
	protected abstract String getNeutralizationMessage();

}
