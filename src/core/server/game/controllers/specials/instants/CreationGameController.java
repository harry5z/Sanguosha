package core.server.game.controllers.specials.instants;

import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;

public class CreationGameController extends SingleTargetInstantSpecialGameController {

	public CreationGameController(PlayerCompleteServer source) {
		super(source, source);
	}

	@Override
	protected void takeEffect(GameInternal game) {
		game.pushGameController(new ReceiveCardsGameController(source, game.getDeck().drawMany(2)));
		this.nextStage();
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.source + " used Creation, use Nullification?";
	}

}
