package core.server.game.controllers.specials.instants;

import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.specials.AbstractSpecialGameController;
import core.server.game.controllers.specials.SpecialGameController.SpecialStage;

public abstract class AbstractInstantSpecialGameController extends AbstractSpecialGameController<SpecialStage> {

	protected PlayerCompleteServer source;
	
	public AbstractInstantSpecialGameController(PlayerInfo source, Game game) {
		super(game);
		this.source = game.findPlayer(source);
	}
	
	@Override
	protected final SpecialStage getInitialStage() {
		return SpecialStage.TARGET_LOCKED;
	}
	
}
