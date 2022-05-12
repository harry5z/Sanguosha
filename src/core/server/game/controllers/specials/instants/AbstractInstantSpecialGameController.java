package core.server.game.controllers.specials.instants;

import core.player.PlayerCompleteServer;
import core.server.game.controllers.specials.AbstractSpecialGameController;
import core.server.game.controllers.specials.SpecialGameController.SpecialStage;

public abstract class AbstractInstantSpecialGameController extends AbstractSpecialGameController<SpecialStage> {

	protected final PlayerCompleteServer source;
	
	public AbstractInstantSpecialGameController(PlayerCompleteServer source) {
		this.source = source;
	}
	
	@Override
	protected final SpecialStage getInitialStage() {
		return SpecialStage.LOADED;
	}
	
}
