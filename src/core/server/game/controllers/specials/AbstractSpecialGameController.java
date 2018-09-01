package core.server.game.controllers.specials;

import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;

public abstract class AbstractSpecialGameController extends AbstractGameController implements SpecialGameController {
	
	protected boolean neutralized;
	protected int neutralizedCount;

	public AbstractSpecialGameController(Game game) {
		super(game);
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
