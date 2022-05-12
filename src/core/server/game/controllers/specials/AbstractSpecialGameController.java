package core.server.game.controllers.specials;

import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.GameControllerStage;

public abstract class AbstractSpecialGameController<T extends GameControllerStage<?>> extends AbstractGameController<T> implements SpecialGameController {
	
	protected boolean nullified;
	protected int nullifiedCount;

	public AbstractSpecialGameController() {
		this.nullified = false;
		this.nullifiedCount = 0;
	}

	@Override
	public void onNullified() {
		this.nullified = !this.nullified;
		this.nullifiedCount = 0;
	}
	
	@Override
	public void onNullificationCanceled() {
		this.nullifiedCount++;
	}
	
	protected abstract String getNullificationMessage();

}
