package core.server.game.controllers.specials.instants;

import core.player.PlayerCompleteServer;
import core.server.game.Game;

public class CreationGameController extends SingleTargetInstantSpecialGameController {

	public CreationGameController(PlayerCompleteServer source) {
		super(source, source);
	}

	@Override
	protected void takeEffect(Game game) {
		game.drawCards(this.source, 2);
		this.nextStage();
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.source + " used Creation, use Nullification?";
	}

}
