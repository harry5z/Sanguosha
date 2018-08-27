package core.server.game.controllers.specials.instants;

import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.specials.AbstractSpecialGameController;

public abstract class AbstractInstantSpecialGameController extends AbstractSpecialGameController {

	protected SpecialStage stage;
	protected PlayerCompleteServer source;
	
	public AbstractInstantSpecialGameController(PlayerInfo source, Game game) {
		super(game);
		this.stage = SpecialStage.TARGET_LOCKED;
		this.source = game.findPlayer(source);
	}
}
